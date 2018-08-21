/**
 * 判断字符串是否为空
 * @param {} str
 * @return {Boolean}
 */
function isEmpty(str) {
	if(str == undefined||str==""){
		return true;
	}else{
		return false;
	}
}
/**
 * 判断字符串是否非空
 * @param {} str
 * @return {Boolean}
 */
function isNotEmpty(str) {
	if(str == undefined||str==""){
		return false;
	}else{
		return true;
	}
}
/**
 * 得到某个字符出现的次数
 * @param {} mainStr
 * @param {} subStr
 * @return {}
 */

 function countInstances(mainStr, subStr)
    {
        var count = 0;
        var offset = 0;
        do
        {
            offset = mainStr.indexOf(subStr, offset);
            if(offset != -1)
            {
                count++;
                offset += subStr.length;
            }
        }while(offset != -1)
        return count;
    }
 /**
 *转换小数，如转换为百分比：FormatNumber(0.0756035338495136*100,2)+'%'
 */
function percentNumber(srcStr,nAfterDot){
	if(srcStr==null||srcStr==''){
		return '';
	}
　　var srcStr,nAfterDot;
　　var resultStr,nTen;
　　srcStr = ""+srcStr+"";
　　strLen = srcStr.length;
　　dotPos = srcStr.indexOf(".",0);
　　if (dotPos == -1){
　　　　resultStr = srcStr+".";
　　　　for (i=0;i<nAfterDot;i++){
　　　　　　resultStr = resultStr+"0";
　　　　}
　　　　return resultStr;
　　}
　　else{
　　　　if ((strLen - dotPos - 1) >= nAfterDot){
　　　　　　nAfter = dotPos + nAfterDot + 1;
　　　　　　nTen =1;
　　　　　　for(j=0;j<nAfterDot;j++){
　　　　　　　　nTen = nTen*10;
　　　　　　}
　　　　　　resultStr = Math.round(parseFloat(srcStr)*nTen)/nTen;
　　　　　　return resultStr;
　　　　}
　　　　else{
　　　　　　resultStr = srcStr;
　　　　　　for (i=0;i<(nAfterDot - strLen + dotPos + 1);i++){
　　　　　　　　resultStr = resultStr+"0";
　　　　　　}
　　　　　　return resultStr;
　　　　}
　　}
 }

/**
 * 小数转换为百分数
 * @param {} srcStr
 * @return {}
 */
function formatNumber(m){
	if(m==null||m==''){
		return '';
	}else if(m==0){
		return '0%';
	}
 	return parseFloat(m)*100+'%';
}    
/**
*在左边补齐字符，string原字符串，addchar补齐字符串，n补齐位数，用例：如果10要补齐4为，补齐字符为0，则用fillZeroLeft(10,0,4),可以得到'0010'
*/
function fillZeroLeft(string,addchar,n){
	var len=string.length;
	for(var i=0;i<n-len;i++){
		string=addchar.toString()+string;
	}
	return string;
}

/**
 * 加法函数
 * @param {} arg1
 * @param {} arg2
 * @return {}
 */
function accAdd(arg1,arg2){   
    var r1,r2,m;   
    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}   
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}   
    m=Math.pow(10,Math.max(r1,r2))   
    return (arg1*m+arg2*m)/m;   
}   
//给Number类型增加一个add方法，调用起来更加方便。   
Number.prototype.add = function (arg){   
    return accAdd(arg,this);   
}  
  
//减法函数  
function subtr(arg1,arg2){  
    var r1,r2,m,n;  
    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}  
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}  
    m=Math.pow(10,Math.max(r1,r2));  
    n=(r1>=r2)?r1:r2;  
    return ((arg1*m-arg2*m)/m).toFixed(n);  
}  
Number.prototype.sub = function (arg){   
    return subtr(arg,this);   
}  
  
//乘法函数  
function accMul(arg1,arg2)   
{   
    var m=0,s1=arg1.toString(),s2=arg2.toString();   
    try{m+=s1.split(".")[1].length}catch(e){}   
    try{m+=s2.split(".")[1].length}catch(e){}   
    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)   
}   
Number.prototype.mul = function (arg){   
    return accMul(arg, this);   
}   
  
//除法函数  
function accDiv(arg1,arg2){   
    var t1=0,t2=0,r1,r2;   
    try{t1=arg1.toString().split(".")[1].length}catch(e){}   
    try{t2=arg2.toString().split(".")[1].length}catch(e){}   
    with(Math){   
    r1=Number(arg1.toString().replace(".",""))   
    r2=Number(arg2.toString().replace(".",""))   
    return accMul((r1/r2),pow(10,t2-t1));   
    }  
}   
Number.prototype.div = function (arg){   
    return accDiv(this, arg);   
}  
