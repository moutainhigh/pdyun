//检查号码是否符合规范，包括长度，类型
 export function isCardNo(card) {
  //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
  var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
  if(reg.test(card) === false)
  {
    return false;
  }else {
    card = card.slice(6,10);
    let curYear = new Date().getFullYear() - card;
    if(!(curYear >= 18 && curYear <= 60)){
      return false;
    }else {
      return true;
    }
  }


};
