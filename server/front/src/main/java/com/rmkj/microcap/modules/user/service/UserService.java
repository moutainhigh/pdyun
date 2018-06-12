package com.rmkj.microcap.modules.user.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.rmkj.microcap.common.bean.*;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.interceptor.ContextInterceptor;
import com.rmkj.microcap.common.interceptor.MybatisPagerInterceptor;
import com.rmkj.microcap.common.modules.pay.yizhifu.CheckUtils;
import com.rmkj.microcap.common.modules.pay.yizhifu.CityUtils;
import com.rmkj.microcap.common.modules.sys.service.TokenService;
import com.rmkj.microcap.common.modules.weixin.bean.ResponseToken;
import com.rmkj.microcap.common.modules.weixin.bean.WeiXinUserInfo;
import com.rmkj.microcap.common.modules.weixin.service.WeiXinLoginService;
import com.rmkj.microcap.common.modules.weixin.service.WeiXinService;
import com.rmkj.microcap.common.utils.AuthContext;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.common.utils.ValidateCodeUtils;
import com.rmkj.microcap.modules.chanong.user.bean.ChangePwdBean;
import com.rmkj.microcap.modules.chanong.user.bean.ForgetPwdBean;
import com.rmkj.microcap.modules.chanong.user.bean.RegisterBean;
import com.rmkj.microcap.modules.corps.bean.CreateCorpsBean;
import com.rmkj.microcap.modules.corps.service.CorpsService;
import com.rmkj.microcap.modules.index.dao.ParameterSetDao;
import com.rmkj.microcap.modules.money.entity.MoneyRecord;
import com.rmkj.microcap.modules.trade.entity.Trade;
import com.rmkj.microcap.modules.user.bean.*;
import com.rmkj.microcap.modules.user.dao.*;
import com.rmkj.microcap.modules.user.entity.*;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by renwp on 2016/10/12.
 */
@Service
@Transactional
public class UserService {

    private static Logger logger = Logger.getLogger(ContextInterceptor.class);

    static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMessageDao userMessageDao;

    @Autowired
    private Ml3AgentDao ml3AgentDao;

    @Autowired
    private Ml3AgentRoleDao ml3AgentRoleDao;

    @Autowired
    private Ml3MemberUnitsDao ml3MemberUnitsDao;

    @Autowired
    private ParameterSetDao parameterSetDao;

    @Autowired
    private WeiXinLoginService weiXinLoginService;

    @Autowired
    private WeiXinService weiXinService;

    @Autowired
    private CorpsService corpsService;

    @Autowired
    private Ml3MemberUnitsService ml3MemberUnitsService;

    public User loginWeiXin(String code) throws Exception {
        String openId = null;
        ResponseToken responseTokenBean = null;
        // 获取openid
        if(ProjectConstants.PRO_DEBUG){
            openId = code;
        }else{
            responseTokenBean = weiXinLoginService.getOAuthToken(code);
            openId = responseTokenBean.getOpenid();
            if(openId == null || "".equals(openId)){
                logger.error("找不到微信用户");
                throw new Exception("找不到微信用户");
            }
        }

        User queryUser = new User();
        queryUser.setOpenId(openId);

        // 根据openid在数据库中查找用户
        User user = userDao.findUser(queryUser);
        if(user == null){
            user = new User();
            if(ProjectConstants.PRO_DEBUG){
                user.setUserHeader("http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46");
            }
            user.setOpenId(openId);
            user.preInsert();
            userDao.registerUser(user);
        }else if(!Constants.USER_STATUE.VALID.equals(user.getStatus().toString())){
            // 停用
            return user;
        }

        // 每次网页授权登录 更新用户微信信息
        if(!ProjectConstants.PRO_DEBUG){
            WeiXinUserInfo weiXinUserInfo = weiXinLoginService.userinfo(responseTokenBean);
            if(weiXinUserInfo != null){
                userDao.updateWeiXinInfo(weiXinUserInfo);
            }
        }

        user = userDao.findUserById(user.getId());

        String token = TokenService.handleToken(AuthContext.getCurAuth().getTerminal(), user.getId());
        user.setToken(token);
        return user;
    }

    /**
     * 更新用户微信信息
     * @param openId
     */
    public void updateUserWeiXinInfo(String openId){
        WeiXinUserInfo weiXinUserInfo = weiXinService.userInfo(openId);
        if(weiXinUserInfo != null){
            userDao.updateWeiXinInfo(weiXinUserInfo);
        }
    }

    /**
     *
     * 必须归到某个代理下面
     * @param registerBean
     */
    public ResponseEntity reg(RegisterBean registerBean) {
        AuthEntity auth = AuthContext.getCurAuth();
        User _user = userDao.findUserByMobile(registerBean.getMobile());
        if(_user != null){
            return new ResponseErrorEntity(StatusCode.MOBILE_EXIST, HttpStatus.BAD_REQUEST);
        }
        /*if(!ValidateCodeUtils.mobileValid(registerBean.getMobile(), registerBean.getVerifyCode(), Constants.ValidateCode.REG)){
            return new ResponseErrorEntity(StatusCode.VALIDATE_CODE_ERROR, HttpStatus.BAD_REQUEST);
        }*/

        User armyUser = null;
        // 校验邀请手机号
        if(StringUtils.isNotBlank(registerBean.getRefereePhone())){
            //根据邀请人手机号查询邀请码
            armyUser = userDao.findUserByMobile(registerBean.getRefereePhone());
            if(null == armyUser){
                return new ResponseErrorEntity(StatusCode.AGENT_INVITE_MOBILE_ERROR, HttpStatus.BAD_REQUEST);
            }
            registerBean.setAgentInviteCode(armyUser.getAgentInviteCode());
        }else{
            return new ResponseErrorEntity(StatusCode.AGENT_VALID_CODE_REQUIRE, HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setChnName(registerBean.getChnName());
        user.setMobile(registerBean.getMobile());
        user.setSubFlag("1");
        user.setAgentInviteCode(registerBean.getAgentInviteCode());
        user.setTradePassword(Utils.md5(registerBean.getPassword()));

        //判断是否是本地注册
        if(StringUtils.isNotEmpty(registerBean.getRegType()) && Constants.RegType.SHOP_REG.equals(registerBean.getRegType())){
            user.setRegType(registerBean.getRegType());
        }else{
            user.setRegType(Constants.RegType.PINDAO_REG);
        }

        // 关联军团关系
        /*if(StringUtils.isNotBlank(registerBean.getUserId()) || registerBean.getAgentInviteCode().length() == 11){
            User userById = userDao.findUserById(registerBean.getUserId());
            user.setParent2Id(userById.getId());
            user.setParent3Id(userById.getParent2Id());
            regSendMsg(user.getParent2Id(), user.getMobile(), "骑兵团");
            regSendMsg(user.getParent3Id(), user.getMobile(), "步兵团");
        }*/
        //关联军团关系 手机号 + userId 或 邀请码+userId注册时写入邀请码
        if((null != armyUser && StringUtils.isNotBlank(registerBean.getUserId())) || (null != armyUser && StringUtils.isNotBlank(registerBean.getMobile()))){
            user.setParent2Id(armyUser.getId());
            user.setParent3Id(armyUser.getParent2Id());
            regSendMsg(user.getParent2Id(), user.getMobile(), "骑兵团");
            regSendMsg(user.getParent3Id(), user.getMobile(), "步兵团");
        }else{
            /*user.setParent2Id(armyUser.getId());
            user.setParent3Id(armyUser.getParent2Id());*/
            regSendMsg(user.getParent2Id(), user.getMobile(), "骑兵团");
            regSendMsg(user.getParent3Id(), user.getMobile(), "步兵团");
        }

        // 第三方注册，微信
        if(registerBean.isThirdReg()){
            if(StringUtils.isBlank(auth.getUserId())){
                return new ResponseErrorEntity(StatusCode.VALIDATION_FAILED, HttpStatus.BAD_REQUEST);
            }
            user.setId(auth.getUserId());
            if(userDao.reg(user) != 1){
                return new ResponseErrorEntity(StatusCode.VALIDATION_FAILED, HttpStatus.BAD_REQUEST);
            }
        }else {
            user.preInsert();
            if(userDao.insert(user) != 1){
                return new ResponseErrorEntity(StatusCode.VALIDATION_FAILED, HttpStatus.BAD_REQUEST);
            }
            auth.setUserId(user.getId());
            auth.setTerminal(registerBean.getTerminal());
        }

        // 注册之后直接创建军团
        CreateCorpsBean createCorpsBean = new CreateCorpsBean();
        createCorpsBean.setIdCard("001");
        createCorpsBean.setTradePassword(user.getTradePassword());
        corpsService.create(createCorpsBean);

        ValidateCodeUtils.removeMobileValidateCode(registerBean.getMobile(), Constants.ValidateCode.REG);
        auth.setToken(TokenService.handleToken(registerBean.getTerminal(), auth.getUserId()));

        return new ResponseEntity(auth, HttpStatus.OK);
    }

    private void regSendMsg(String userId, String mobile, String msg){
        if(userId == null || "".equals(userId)){
            return;
        }
        UserMessage userMessage = new UserMessage();
        userMessage.setUserId(userId);
        userMessage.setTitle("新会员加入");
        userMessage.setContent(Utils.formatStr("{0}加入你的{1}",
                mobile.substring(0,3).concat("*****").concat(mobile.substring(8,11)), msg));
        userMessage.preInsert();
        userMessageDao.record(userMessage);
    }

    /**
     * 登录
     * @param loginBean
     * @return
     */
    public ResponseEntity login(LoginBean loginBean, String path, Map map) {
        User user = userDao.findUserByMobile(loginBean.getMobile());
        if(user == null){
            return new ResponseErrorEntity(StatusCode.ACCOUNT_NO_EXIST, HttpStatus.BAD_REQUEST);
        }
        //判断会员登录请求的二级域名
        if(!ProjectConstants.TWO_LEVEL_DOMAIN_DEBUG) {
            int endIndex = path.indexOf(".");
            String twoLevelDomain = path.substring(0,endIndex);
            user.setTwoLevelDomain(twoLevelDomain);
            if (null == userDao.getUserTwoLevelDomain(user)) {
                return new ResponseErrorEntity(StatusCode.BAD_REQUEST_ADDRESS, HttpStatus.NOT_FOUND);
            }
        }

        if(!user.getTradePassword().equals(Utils.md5(loginBean.getPassword()))){
            return new ResponseErrorEntity(StatusCode.PASSWORD_ERROR, HttpStatus.BAD_REQUEST);
        }

        if(Constants.USER_STATUE.INVALID.equals(user.getStatus().toString())){
            return new ResponseErrorEntity(StatusCode.USER_CLOSE, HttpStatus.BAD_REQUEST);
        }

        AuthEntity authEntity = new AuthEntity();
        authEntity.setUserId(user.getId());
        authEntity.setTerminal(loginBean.getTerminal());
        authEntity.setToken(TokenService.handleToken(authEntity.getTerminal(), user.getId()));
        map.put("user",user);
        return new ResponseEntity(authEntity, HttpStatus.OK);
    }


    /**
     *
     * @return
     */
    public ResponseEntity get() {
        AuthEntity auth = AuthContext.getCurAuth();
        User user = userDao.findUserById(auth.getUserId());
        user.setTradePassword(null);
        return new ResponseEntity(user, HttpStatus.OK);
    }

    /**
     *
     * @param pageEntity
     * @return
     */
    public PagerBean<Trade> tradeList(PageEntity pageEntity) {
        User user = toUser(pageEntity);
        List<Trade> list = userDao.tradeList(user);
        return new PagerBean<Trade>(list, MybatisPagerInterceptor.getTotal());
    }

    /**
     *
     * @param pageEntity
     * @return
     */
    public PagerBean<MoneyRecord> moneyRecordList(PageEntity pageEntity) {
        User user = toUser(pageEntity);
        List<MoneyRecord> list = userDao.moneyRecordList(user);
        return new PagerBean<MoneyRecord>(list, MybatisPagerInterceptor.getTotal());
    }

    private User toUser(PageEntity pageEntity){
        AuthEntity curAuth = AuthContext.getCurAuth();

        User user = new User();
        user.setStart(pageEntity.getStart());
        user.setRows(pageEntity.getRows());
        user.setId(curAuth.getUserId());
        return user;
    }

    /**
     *
     * @param moneyRecord
     * @return
     */
    public MoneyRecord moneyOutInfo(MoneyRecord moneyRecord) {
        moneyRecord.setUserId(AuthContext.getUserId());
        return userDao.moneyRecord(moneyRecord);
    }

    public ResponseEntity modifyChnName(ModifyChnNameBean modifyChnNameBean) {
        User user = new User();
        user.setChnName(modifyChnNameBean.getChnName());
        user.setId(AuthContext.getUserId());
        if(userDao.update(user) != 1){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity modifyMobile(ModifyMobile modifyMobile) {
        User _user = userDao.findUserByMobile(modifyMobile.getMobile());
        if(_user != null && !_user.getId().equals(AuthContext.getUserId())){
            return new ResponseErrorEntity(StatusCode.MOBILE_EXIST, HttpStatus.BAD_REQUEST);
        }

        if(!ValidateCodeUtils.mobileValid(modifyMobile.getMobile(), modifyMobile.getValidCode(), Constants.ValidateCode.M_MOBILE)){
            return new ResponseErrorEntity(StatusCode.VALIDATE_CODE_ERROR, HttpStatus.BAD_REQUEST);
        }

        User userById = userDao.findUserById(AuthContext.getUserId());
        if(userById == null || !userById.getTradePassword().equals(modifyMobile.getTradePassword())){
            return new ResponseErrorEntity(StatusCode.TRADE_PASSWORD_INVALID, HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setMobile(modifyMobile.getMobile());
        user.setId(AuthContext.getUserId());
        if(userDao.update(user) != 1){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        ValidateCodeUtils.removeMobileValidateCode(modifyMobile.getMobile(), Constants.ValidateCode.M_MOBILE);
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity modifyTradePwd(ModifyTradePwd modifyTradePwd) {
        User userById = userDao.findUserById(AuthContext.getUserId());
/*        if(!ValidateCodeUtils.mobileValid(userById.getMobile(), modifyTradePwd.getValidCode(), Constants.ValidateCode.M_TRADE_PWD)){
            return new ResponseErrorEntity(StatusCode.VALIDATE_CODE_ERROR, HttpStatus.BAD_REQUEST);
        }*/
        //验证原密码
        modifyTradePwd.setPreTradePassword(Utils.md5(modifyTradePwd.getPreTradePassword()));
        if(!userById.getTradePassword().equals(modifyTradePwd.getPreTradePassword())){
            return new ResponseErrorEntity(StatusCode.PASSWORD_ERROR, HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setTradePassword(Utils.md5(modifyTradePwd.getTradePassword()));
        user.setId(AuthContext.getUserId());
        if(userDao.update(user) != 1){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        //ValidateCodeUtils.removeMobileValidateCode(userById.getMobile(), Constants.ValidateCode.M_TRADE_PWD);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 重置用户密码
     */
    public ResponseEntity validateMobileCode(FirstPartResetTradePwd firstPartResetTradePwd){
        User user = userDao.findUserByMobile(firstPartResetTradePwd.getMobile());
        if(user == null ){
            return new ResponseErrorEntity(StatusCode.MOBILE_NOT_EXIST, HttpStatus.BAD_REQUEST);
        }
        if(!ValidateCodeUtils.mobileValid(firstPartResetTradePwd.getMobile(), firstPartResetTradePwd.getValidCode(),Constants.ValidateCode.M_TRADE_PWD)){
            return new ResponseErrorEntity(StatusCode.VALIDATE_CODE_ERROR, HttpStatus.BAD_REQUEST);
        }
        //第二次验证清理验证码
        //ValidateCodeUtils.removeMobileValidateCode(user.getMobile(), Constants.ValidateCode.M_TRADE_PWD);
        return new ResponseEntity(HttpStatus.OK);
    }
    public ResponseEntity restTradePass(SecondPartResetTradePwd secondPartResetTradePwd){
        User user = userDao.findUserByMobile(secondPartResetTradePwd.getMobile());
        user.setTradePassword(Utils.md5(secondPartResetTradePwd.getTradePassword()));
        if(!ValidateCodeUtils.mobileValid(secondPartResetTradePwd.getMobile(), secondPartResetTradePwd.getValidCode(),Constants.ValidateCode.M_TRADE_PWD)){
            return new ResponseErrorEntity(StatusCode.VALIDATE_CODE_ERROR, HttpStatus.BAD_REQUEST);
    }
        ValidateCodeUtils.removeMobileValidateCode(user.getMobile(), Constants.ValidateCode.M_TRADE_PWD);
        if(userDao.resetUserTradePwd(user) != 1){
            return new ResponseErrorEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 用户消息查询
     * @param userMessage
     * @return
     */
    public PagerBean<UserMessage> messageList(UserMessage userMessage) {
        userMessage.setUserId(AuthContext.getUserId());
        List<UserMessage> list = userMessageDao.findList(userMessage);
        return new PagerBean<UserMessage>(list, MybatisPagerInterceptor.getTotal());
    }

    /**
     * 查询用户信息详情
     */
    public UserMessage selectUserMsgDetail(UserMessage userMessage){
        userMessage.setUserId(AuthContext.getUserId());
        UserMessage um = userMessageDao.findUserMsgDetail(userMessage);
        return um;
    }
    /**
     * 用户新消息数量
     * @return
     */
    public long countNewMessage() {
        return userMessageDao.countNewMessage(AuthContext.getUserId());
    }

    /**
     * 标记用户消息为已读
     * @param id
     * @return
     */
    public ResponseEntity readMessage(String id) {
        UserMessage userMessage = new UserMessage();
        userMessage.setUserId(AuthContext.getUserId());
        userMessage.setId(id);
        if (userMessageDao.readMessage(userMessage) != 1) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 更新登录
     * @param clientIp
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public void updateLogin(String clientIp) {
        User user = new User();
        user.setLastLoginIp(clientIp);
        user.setId(AuthContext.getUserId());
        user.setLastLoginTime(new Date());
        userDao.updateLogin(user);
    }

    /**
     *
     * @param addBankCard
     * @return
     */
    @Transactional
    public ResponseEntity addBankCard(AddBankCard addBankCard) {
        String userId = AuthContext.getUserId();
//        if(userDao.findBankCard(userId) != null){
//            return new ResponseErrorEntity(StatusCode.BANK_CARD_IS_BOUND, HttpStatus.BAD_REQUEST);
//        }
        userDao.deleteBankCard(userId);

        User userById = userDao.findUserById(userId);
        if(!userById.getTradePassword().equals(addBankCard.getPassword())){
            return new ResponseErrorEntity(StatusCode.PASSWORD_ERROR, HttpStatus.BAD_REQUEST);
        }

        if(CheckUtils.getLianHangHao(addBankCard.getBankName()) == null){
            return new ResponseErrorEntity(StatusCode.YIZHIFU_NOT_FIND_BANK_LIANHANGHAO, HttpStatus.BAD_REQUEST);
        }

        if(!CheckUtils.checkProvince(addBankCard.getProvince())){
            return new ResponseErrorEntity(StatusCode.YIZHIFU_PROVINCE_UNVALID, HttpStatus.BAD_REQUEST);
        }

        if(!CheckUtils.checkCity(addBankCard.getCity())){
            return new ResponseErrorEntity(StatusCode.YIZHIFU_CITY_UNVALID, HttpStatus.BAD_REQUEST);
        }

        String str = JSON.toJSONString(addBankCard);
        BankCard bankCard = JSON.parseObject(str, BankCard.class);
        bankCard.setUserId(userId);
        bankCard.preInsert();
        userDao.addBankCard(bankCard);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     *
     * @return
     */
    public BankCard findBankCard(){
        return userDao.findBankCard(AuthContext.getUserId());
    }

    public void shareImg(ServletOutputStream writer) {
        User userById = userDao.findUserById(AuthContext.getUserId());

        File file = new File(UserService.class.getResource("/").getPath().concat("shareImg.jpg"));
        try {
            if(userById.getTicket() != null){
//                Thumbnails.of(file)
//                    .scale(1f)
//                    .watermark(new Position() {
//                        @Override
//                        public Point calculate(int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
//                            return new Point(144, 508);
//                        }
//                    }, Thumbnails.of(ImageIO.read(new URL("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" .concat(userById.getTicket()))))
//                            .size(170, 170)
//                            .watermark(Positions.CENTER, Thumbnails.of(ImageIO.read(new URL(userById.getUserHeader()))).size(30, 30).asBufferedImage(), 1f)
//                            .outputQuality(1f).imageType(1).asBufferedImage(), 1f)
//                    .outputQuality(1f).outputFormat("jpg").toOutputStream(writer);
                Thumbnails.of(ImageIO.read(new URL("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=".concat(userById.getTicket()))))
                        .size(370, 370)
                        .watermark(Positions.CENTER, Thumbnails.of(ImageIO.read(new URL(userById.getUserHeader()))).size(60, 60).asBufferedImage(), 1f)
                        .outputQuality(1f).imageType(1).outputFormat("jpg").toOutputStream(writer);
            }else {
                Thumbnails.of(file).scale(1f).outputQuality(1f).outputFormat("jpg").toOutputStream(writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * TODO 生成带图片的微信二维码
     */

    public BufferedImage generateRQCodeImage(ServletOutputStream write){
        String userId = AuthContext.getUserId();
        User user = userDao.findUserById(userId);
        Ml3Agent ml3Agent = null;
        if(null == user.getAgentInviteCode()){
            ml3Agent = new Ml3Agent();
            ml3Agent.setUserId(user.getId());
            ml3Agent = ml3AgentDao.findMl3AgentSelectived(ml3Agent);
        }else {
            //查询登录会员的代理的邀请码 : agent_invite_code
            ml3Agent = ml3AgentDao.findMl3AgentSelective(user.getAgentInviteCode());
        }
        //根据会员的代理信息中的unitsid查询会员单位全部信息 ： two_level_domain
        Ml3MemberUnits ml3MemberUnits = new Ml3MemberUnits();
        ml3MemberUnits.setId(ml3Agent.getUnitsId());
        ml3MemberUnits = ml3MemberUnitsDao.findMl3MemberUnits(ml3MemberUnits);

        String url = "http://"+ ml3MemberUnits.getTwoLevelDomain() + "/front/wap/reg?agentInviteCode=" + ml3Agent.getAgentInviteCode() + "&userId=" + user.getId();
        //获取resource文件夹中的图片
        String imageUrl = this.getClass().getClassLoader().getResource("shareImg.jpg").getPath();
        return Utils.generateORCodeImage(url, imageUrl);
    }

    /**
     * TODO 生成微信二维码zxing
     */
    public BufferedImage generateRQCode(ServletOutputStream write) {
        String userId = AuthContext.getUserId();
        User user = userDao.findUserById(userId);
        Ml3Agent ml3Agent = null;
        if (null != user.getAgentInviteCode()) {
            //查询登录用户的代理的邀请码 : agent_invite_code
            ml3Agent = ml3AgentDao.findMl3AgentSelective(user.getAgentInviteCode());
        } else {
            ml3Agent = new Ml3Agent();
            ml3Agent.setUserId(user.getId());
            ml3Agent = ml3AgentDao.findMl3AgentSelectived(ml3Agent);
        }

        //根据会员的代理信息中的unitsid查询会员单位全部信息 ： two_level_domain
        Ml3MemberUnits ml3MemberUnits = new Ml3MemberUnits();
        ml3MemberUnits.setId(ml3Agent.getUnitsId());
        ml3MemberUnits = ml3MemberUnitsDao.findMl3MemberUnits(ml3MemberUnits);
        String url = parameterSetDao.findParameterSet().getQrCodeUrl();
        if (!StringUtils.isBlank(url)){
            url = Utils.formatStr(url, ml3MemberUnits.getTwoLevelDomain(),ml3Agent.getAgentInviteCode(),user.getId());
        }
        byte [] imageByte = Utils.generateOrCode(url);

        return base64StringToImage(encoder.encode(imageByte).trim(), write);

        /*return encoder.encodeBuffer(imageByte).trim();*/
    }

    /**
     * 返回二维码url
     * @return
     */
    public Map<String,Object> getSubRQCode(){
        Map<String,Object> ret = new HashedMap();
        String userId = AuthContext.getUserId();
        User user = userDao.findUserById(userId);
//        String url = parameterSetDao.findParameterSet().getQrCodeUrl();
//        if (!StringUtils.isBlank(url)){
//            url = Utils.formatStr(url,user.getMobile(),user.getId());
//        }
        ret.put("refereePhone",user.getMobile());
        ret.put("inviteId",user.getId());
        return ret;
    }

    /**
     * TODO 转换byte字节流-->微信图片
     * @param base64String
     * @param write
     * @return
     */
    public BufferedImage base64StringToImage(String base64String, ServletOutputStream write){
        try {
            byte[] bytes1 = decoder.decodeBuffer(base64String);

            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bufferedImage =ImageIO.read(bais);

            //ImageIO.write(bi1, "jpg", write);//不管输出什么格式图片，此处不需改动
            return bufferedImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getAgentInviteCode(String mobile){
        User user = userDao.findUserByMobile(mobile);
        Ml3Agent ml3Agent = ml3AgentDao.findMl3AgentSelective(user.getAgentInviteCode());
        Ml3MemberUnits ml3MemberUnits = new Ml3MemberUnits();
        ml3MemberUnits.setId(ml3Agent.getUnitsId());
        return ml3MemberUnitsDao.findMl3MemberUnits(ml3MemberUnits).getId();
    }

    /**
     * TODO 检查银行卡是否存在
     * @return
     */
    public String validateBankCard(Model model){
        if(null == userDao.findBankCard(AuthContext.getUserId())){
            findCheckUtils(model);
            return "wap/user/Withdrawals";
        }else{
            model.addAttribute("user", userDao.findUserById(AuthContext.getUserId()));
            findCheckUtils(model);
            return "wap/user/moneyOut";
        }
    }

    /**
     * TODO 获取省、市、银行
     * @param model
     */
    public void findCheckUtils(Model model){
        //获取省份
        List<String> list = CityUtils.getProvinceList();
        Map<String,String> map = CityUtils.getLianHangHaoMap();
        model.addAttribute("_province", list);
        model.addAttribute("_bank", CheckUtils.findBankName());
        model.addAttribute("province", JSON.toJSONString(list));
        model.addAttribute("bank", JSON.toJSONString(map));
    }

    /**
     * 模糊查询银行支行
     * @param mainWord
     * @return
     */
    public List<String> findOpenBankName(String mainWord){
        if(StringUtils.isEmpty(mainWord)){
            return null;
        }
        String[] strs =  mainWord.trim().split("");
        StringBuffer sb = new StringBuffer();
        for(String s:strs){
            sb.append("%").append(s);
        }
        sb.append("%");
        mainWord = sb.toString();
        return userDao.findOpenBankName(mainWord);
    }

    /**
     * 忘记密码
     * @param forgetPwdBean
     * @return
     */
    public ResponseEntity forgetPassword(ForgetPwdBean forgetPwdBean){
        Map<String,Object> ret = new HashedMap();
        if(!ValidateCodeUtils.mobileValid(forgetPwdBean.getMobile(), forgetPwdBean.getVerifyCode(), Constants.ValidateCode.M_MOBILE)){
            return new ResponseErrorEntity(StatusCode.VALIDATE_CODE_ERROR, HttpStatus.BAD_REQUEST);
        }else{
            forgetPwdBean.setNewPwd(Utils.md5(forgetPwdBean.getNewPwd()));
            userDao.forgetPassword(forgetPwdBean);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改密码
     * @param changePwdBean
     * @return
     */
    public ResponseEntity updatePassword(ChangePwdBean changePwdBean){
        String oldPwd = userDao.getUserPassword(AuthContext.getUserId());
        if(!oldPwd.equals(Utils.md5(changePwdBean.getOldPwd()))){
            return new ResponseErrorEntity(StatusCode.OLD_PASSWORD_ERROR, HttpStatus.BAD_REQUEST);
        }
        changePwdBean.setUserId(AuthContext.getUserId());
        changePwdBean.setNewPwd(Utils.md5(changePwdBean.getNewPwd()));
        if(userDao.updatePassword(changePwdBean)!=1){
            return new ResponseErrorEntity(StatusCode.UPDATE_PASSWORD_ERROR, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
