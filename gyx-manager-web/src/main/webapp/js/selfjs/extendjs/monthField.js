Ext.namespace("ntbms")  
ntbms.MonthPicker=Ext.extend(  
    Ext.form.Field,  
    {  
        getValue:function(){  
            var arr=this.el.query("select");  
            var year=arr[0].value;  
            var month=arr[1].value;  
            return year+month;  
        },  
        /**value 格式2000-07*/  
        setValue:function(value){  
            if(Ext.type(value)=="date")  
            {  
                var year=value.getFullYear();  
                var rawMonth=value.getMonth()+1;  
                //console.log(rawMonth);  
                var month=null;  
                if(rawMonth<10)  
                    month="0"+rawMonth;  
                else  
                    month=rawMonth;  
                var arr=this.el.query("select");  
                arr[0].value=year;  
                arr[1].value=month;  
            }  
            else  
                throw new Error();  
        },  
        defaultAutoCreate:function(){  
            var result={tag:"div",children:[]};  
            var year={tag:"select",children:[]};  
            
            for(var i=2010;i<=2050;i++)  
            {  
                year.children.push({  
                    tag:"option",  
                    value:i,  
                    html:i  
                })  
            }  
            var month={tag:"select",children:[]};  
            for(var i=1;i<=12;i++)  
            {  
                if(i<10)  
                    i="0"+i;  
                month.children.push({  
                    tag:"option",  
                    value:i,  
                    html:i  
                });  
            }  
            result.children.push(year);  
            result.children.push({tag:"span",html:"年"});  
            result.children.push(month);  
            result.children.push({tag:"span",html:"月"});  
            
            return result;   
        }()  
    }  
)  
Ext.reg("monthField",ntbms.MonthPicker); 