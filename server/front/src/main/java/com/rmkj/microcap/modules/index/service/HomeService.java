package com.rmkj.microcap.modules.index.service;

import com.alibaba.fastjson.JSON;
import com.rmkj.microcap.common.constants.Constants;
import com.rmkj.microcap.modules.market.service.MarketService;
import com.rmkj.microcap.modules.trade.dao.TradeDao;
import com.rmkj.microcap.modules.trade.entity.Contract;
import com.rmkj.microcap.modules.trade.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by renwp on 2016/10/21.
 */
@Service
@Transactional
public class HomeService {

    @Autowired
    private MarketService marketService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private TradeDao tradeDao;

    public void index(Model model){
        List<Contract> contracts = tradeService.contractList();
        String[] codes = marketService.codes();

        List<Contract> newList = new ArrayList<>();
        newList.addAll(contracts);

        String[] codeArr = removeMarket(newList, codes);
        model.addAttribute("contracts", newList);
        model.addAttribute("_contracts", JSON.toJSONString(newList));
        model.addAttribute("markets", marketService.latest(Constants.Market.GetType.ALL, codeArr));
    }

    /**
     * 移除微盘模式，微交易模式
     * @param list
     * @param codes
     * @return
     */
    public String[] removeMarket(List<Contract> list, String[] codes){
        List<Contract> removeList = new ArrayList<>();
        List<String> codesList = new ArrayList<>();
        for (Contract contract : list){
            if(Constants.ContractModel.MARKET.equals(contract.getModel()) || Constants.ContractModel.TRADE.equals(contract.getModel())){
                removeList.add(contract);

            }else{
                codesList.add(contract.getCode());
            }
        }
        System.out.println(codesList instanceof List);
        list.removeAll(removeList);
        return codesList.toArray(new String[codesList.size()]);
    }


    /**
     * 市场交易
     * @param model
     */
    public void indexMarket(Model model){
        List<Contract> contracts = tradeService.contractList();
        String[] codes = marketService.codes();

        List<Contract> newList = new ArrayList<>();
        newList.addAll(contracts);

        String[] codeArr = removePoint(newList, codes);
        model.addAttribute("contracts", newList);
        model.addAttribute("_contracts", JSON.toJSONString(newList));
        model.addAttribute("markets", marketService.latest(Constants.Market.GetType.ALL, codeArr));
    }

    /**
     * 移除点位模式，微交易模式
     * @param list
     * @param codes
     * @return
     */
    public String[] removePoint(List<Contract> list, String[] codes){
        List<Contract> removeList = new ArrayList<>();
        List<String> codesList = new ArrayList<>();
        for (Contract contract : list){
            if(Constants.ContractModel.TRADE.equals(contract.getModel()) || Constants.ContractModel.POINT.equals(contract.getModel())){
                removeList.add(contract);

            }else{
                codesList.add(contract.getCode());
            }
        }
        System.out.println(codesList instanceof List);
        list.removeAll(removeList);
        return codesList.toArray(new String[codesList.size()]);
    }


}
