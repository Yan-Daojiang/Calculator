import java.util.ArrayList;
import java.util.Stack;

/**
 * @author: Yan_Daojiang
 * @date: 2018/12/18
 * @description:实现核心的计算功能的类，对一个表达式进行解析后计算
 * @Algirithm:算符优先法
 **/

public class Calculate {

    /**************************************
    **函数名及参数：Precede(char t1, char t2)
     **返回类型：char
     **作用：比较两个运算符的优先关系
     ********************************************/
    private static char Precede(char t1, char t2)
    { //判断两符号的优先关系
        char f='!';
        switch (t2){
            case '+':
            case '-':
                if (t1 == '(' || t1 == '#')
                    f = '<';
                else
                    f = '>';
                break;
            case '*':
            case '/':
                if (t1 == '*' || t1 == '/' || t1 == ')')
                    f = '>';
                else
                    f = '<';
                break;
            case '(':
                if (t1 == ')')
                    System.out.println("ERROR");
                else
                    f = '<';
                break;
            case ')':
                switch (t1){
                    case '(':f = '=';
                        break;
                    case '#':
                        System.out.println("ERROR");
                    default: f = '>';
                }
                break;
            case '#':
                switch (t1){
                    case '#':f = '=';
                        break;
                    case '(':
                        System.out.println("ERROR");
                    default:
                        f = '>';
                }
        }
        return f;
    }



    /**************************************
     **函数名及参数：In(char c)
     **返回类型：boolean
     **作用：判断参数中的c是否为运算符，
     * ****是返回true，否返回false
     ********************************************/
    private static boolean In(char c)
    { // 判断c是否为运算符
        switch (c)
        {
            case'+':
            case'-':
            case'*':
            case'/':
            case'(':
            case')':
            case'#':return true;
            default:return false;
        }
    }



    /**************************************
     **函数名及参数：Analysis(String aim)
     **返回类型：ArrayList<String>
     **作用：对目标表达式进行解析，保存能够处理多位数和小数
     ********************************************/
    private static ArrayList<String> Analysis(String aim)
    {
        //解析表达式
        String string=aim+"#";//表达式的尾部加一个#方便后面操作
        StringBuffer curNum = new StringBuffer();//缓冲：用于追加多位数和小数
        ArrayList<String> expression = new ArrayList<>();

        //读取表达式的字符，进行分为后加入expression
        for (int j = 0; j < string.length(); j++) {
            String str = string.charAt(j) + "";
            //当前获取的为数字或者小数点
            if (str.equals("1") || str.equals("2") || str.equals("3") || str.equals("4")
                    || str.equals("5") || str.equals("6") || str.equals("7") || str.equals("8") ||
                    str.equals("9") || str.equals("0") || str.equals(".")) {
                curNum.append(str);//
            } else{
                if(string.charAt(j)=='-'&&j==0){
                    if(curNum.length()>0)//判断
                    {
                        expression.add(curNum.toString());
                        curNum.delete(0,curNum.length());
                        expression.add("0");
                        expression.add("-");
                    }

                    else
                    {
                        expression.add("0");
                        expression.add("-");
                    }
                }
                else if(string.charAt(j)=='('&&string.charAt(j+1)=='-')
                {//中间负数时, 补（0
                    if(curNum.length()>0)
                    {
                        expression.add(curNum.toString());
                        curNum.delete(0,curNum.length());//取出数据后就清空
                        expression.add("(");
                        expression.add("0");
                    }
                    else
                    {
                        expression.add("(");
                        expression.add("0");
                    }

                }
                //直接处理符号
                else{
                    if(curNum.length()>0)
                    {
                        expression.add(curNum.toString());
                        expression.add(string.charAt(j)+"");
                        curNum.delete(0,curNum.length());//取出数据后就清空
                    }
                    else
                    {
                        expression.add(string.charAt(j)+"");
                    }

                }
            }

        }
        return expression;
    }




    /**************************************
     **函数名及参数：Operate(double a, char theta, double b)
     **返回类型：double
     **作用：根据符号栈中弹出的符号，计算从栈中弹出的两个操作数，
     * ****并返回中间结果，准备再次入栈
     ********************************************/
    private static double Operate(double a, char theta, double b)
    {
        //根据运算符进行二元运算的函数实现
        double c=0;//c用于返回结果，初始化为0
        switch (theta)
        {
            case'+':
                c = a + b ;
                break;
            case'-':
                c = a - b ;
                break;
            case'*':
                c = a*b;
                break;
            case'/':
                c = a / b ;
        }
        return c;
    }





    /**************************************
     **函数名及参数：calculation( ArrayList<String> expression）
     **返回类型：double
     **作用：对解析之后的表达式进行求值，并从栈顶返回最终的计算结果
     ********************************************/
    private static double calculation( ArrayList<String> expression)
    {
        Stack<Character>OPTR=new Stack<>();
        Stack<Double>OPND=new Stack<>();

        OPTR.push('#');//向符号栈中压入“#”作为判断

        //expression 转换为字符串数组
        //解析的表达式通过字符串进行存储；s数组中的每个元素为需要进行转换操作的元素
        //如果是数字就转换为double类型，符号就转换为char类型
        String[] s=(String[])expression.toArray(new String[expression.size()]);

        int i=0;

        String c=s[i];

        while(c.charAt(0)!='#'||OPTR.peek()!='#'){
            //是数字时直接加入数字栈
            if(!In(c.charAt(0))){
                OPND.push(Double.parseDouble(c));
                i++;
                c=s[i];
            }
            else
                switch (Precede(OPTR.peek(),c.charAt(0))){
                    case '<'://优先级低就直接压人符号栈，读取下一位
                        OPTR.push(c.charAt(0));
                        i++;
                        c=s[i];
                        break;
                    case'='://脱括号，接收下一个
                        OPTR.pop();
                        i++;
                        c=s[i];
                        break;
                    case'>'://退栈运算，结果入栈
                        char theta=OPTR.pop();
                        double b=OPND.pop();
                        double a=OPND.pop();
                        OPND.push(Operate(a,theta,b));
                        break;
                }

        }
        //System.out.println(OPND.peek());
        return OPND.peek();//返回计算的结果
    }




    /**************************************
     **函数名及参数：cal(String aim)
     **返回类型：double
     **作用：触发计算功能，返回最终结果，便于计算器输出
     ********************************************/
    public static double cal(String aim)
    {
        double result;
        //调用解析函数，对目标表达式进行解析
        ArrayList<String> expression=Analysis(aim);
        //对解析后的表达式进行计算
        result=calculation(expression);
        return result;
    }


}