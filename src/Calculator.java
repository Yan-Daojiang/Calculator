import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.util.ArrayList;
        import java.util.Stack;

/**
 * @author: Yan_Daojiang
 * @date: 2018/12/14
 * @description:包含计算器的界面和设置输入检查，不能通过检查时的点击就不会进行响应
 **/


public class Calculator extends JFrame implements ActionListener {
    //需要进行运算的表达式,用于辅助检查
    private ArrayList<String> list;

    //计算器界面所需要的所有按钮组件
    private JButton btL_br = new JButton("(");
    private JButton btR_br = new JButton(")");
    private JButton bt_AC = new JButton("AC");
    private JButton bt_00 = new JButton("00");
    private JButton btn1 = new JButton("1");
    private JButton btn2 = new JButton("2");
    private JButton btn3 = new JButton("3");
    private JButton btn4 = new JButton("4");
    private JButton btn5 = new JButton("5");
    private JButton btn6 = new JButton("6");
    private JButton btn7 = new JButton("7");
    private JButton btn8 = new JButton("8");
    private JButton btn9 = new JButton("9");
    private JButton btn0 = new JButton("0");
    private JButton bt_plus = new JButton("+");
    private JButton bt_minus = new JButton("-");
    private JButton bt_mul = new JButton("*");
    private JButton bt_divide = new JButton("/");
    private JButton bt_point = new JButton(".");
    private JButton bt_is = new JButton("=");
    //文本框显示计算的结果
    static JTextField resultText = new JTextField("0");
    //设置两个panel用于添加按钮和文本框
    static JPanel key_pan = new JPanel();
    static JPanel result_pan = new JPanel();

    //控制标志
    private boolean vbegin = true;// 控制输入，true为重新输入，false为可以接着输入
    private boolean equals_flag = true;//等号的控制标志
    private boolean isContinueInput = true;// true为正确，可以继续输入，false错误，输入锁定


    /**************************************
     **函数名及参数：go()
     **返回类型：void
     **作用：启动计算器，设置布局，开启监听，
     * ****并初始化一个list接收输入
     ********************************************/
    public void go()
    {
        initLayout();//界面设置
        initAction();//监听
        list = new ArrayList<String>();
    }


    /**************************************
     **函数名及参数：initLayout()
     **返回类型：void
     **作用：初始化布局，
     * ***包含文本框的布局和和按钮的布局及基本设置
     ********************************************/
    public void initLayout()
    {
        //文本框的设置
        resultText.setHorizontalAlignment(JTextField.RIGHT);//设置对齐状态为靠右对齐
        resultText.setEditable(false);//设置结果显示框为不可编辑状态
        resultText.setBackground(Color.YELLOW);
        //设置结果显示部分的布局并添加
        result_pan.setLayout(new BorderLayout());
        result_pan.add(resultText, BorderLayout.CENTER);

        //按钮部分的设置
        //设置运算按键的布局为5*4的网格布局，添加按钮到key_pan
        key_pan.setLayout(new GridLayout(5, 4));
        key_pan.add(btL_br);
        key_pan.add(btR_br);
        key_pan.add(bt_AC);
        key_pan.add(bt_plus);

        key_pan.add(btn7);
        key_pan.add(btn8);
        key_pan.add(btn9);
        key_pan.add(bt_minus);

        key_pan.add(btn4);
        key_pan.add(btn5);
        key_pan.add(btn6);
        key_pan.add(bt_mul);

        key_pan.add(btn1);
        key_pan.add(btn2);
        key_pan.add(btn3);
        key_pan.add(bt_divide);

        key_pan.add(btn0);
        key_pan.add(bt_point);
        key_pan.add(bt_00);
        key_pan.add(bt_is);

    }


    /**************************************
     **函数名及参数：initAction()
     **返回类型：void
     **作用：对所有的组件进行监听
     ********************************************/
    public void initAction()
    {
        btL_br.addActionListener(this);
        btR_br.addActionListener(this);
        bt_AC.addActionListener(this);
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn3.addActionListener(this);
        btn4.addActionListener(this);
        btn5.addActionListener(this);
        btn6.addActionListener(this);
        btn7.addActionListener(this);
        btn8.addActionListener(this);
        btn9.addActionListener(this);
        btn0.addActionListener(this);
        bt_plus.addActionListener(this);
        bt_minus.addActionListener(this);
        bt_mul.addActionListener(this);
        bt_divide.addActionListener(this);
        bt_point.addActionListener(this);
        bt_is.addActionListener(this);
        bt_00.addActionListener(this);
    }


    /**************************************
     **函数名及参数：主函数
     **返回类型：void
     **作用：程序的入口，包含界面的一些基本的设置
     ********************************************/
    public static void main(String[] args)
    {
        Calculator mainJFrame=new Calculator();
        Container container=mainJFrame.getContentPane();
        //主界面的一些基本设置
        mainJFrame.setTitle("计算器1.0");
        mainJFrame.setSize(260,230);
        mainJFrame.setLocationRelativeTo(null);//计算器界面显示在屏幕中央
        mainJFrame.setResizable(false);//设置窗口的大小不可改变
        mainJFrame.setDefaultCloseOperation(3);//设置关闭程序

        //设置主界面的布局，添加文本框和按钮的panel
        //主界面使用JFrame的默认布局
        container.add(result_pan,BorderLayout.NORTH);
        container.add(key_pan,BorderLayout.CENTER);

        //初始化
        mainJFrame.go();
        mainJFrame.setVisible(true);
    }


    /**************************************
     **函数名及参数：actionPerformed(ActionEvent e)
     **返回类型：void
     **作用：组件发生操作时调用
     ********************************************/
    @Override
    public void actionPerformed(ActionEvent e)
    {
        //通过按钮上的标签获取用户点击的是哪个按钮
        String str=e.getActionCommand();
        //输入ac时直接clear
        if(str.equals("AC"))
            clear();
        else
            //点击其他的时候需要先检查后处理
            handle(str);
    }


    /**************************************
     **函数名及参数：clear()
     **返回类型：void
     **作用：用于点击AC和需要将计算器清空的过程
     * **包括清空用于接收的list和文本框修改控制标志和等号标志
     ********************************************/
    private void clear()
    {
        //list清空，文本框初始化为0，vbegin设为true,equal_flag改为true
        list.clear();
        resultText.setText("0");
        vbegin = true;//控制输入表示设为---重新输入
        equals_flag = true;
    }



    /**************************************
     **函数名及参数：handle(String key)
     **返回类型：void
     **作用：处理数字和符号按钮，处理的过程中需要进行检查
     * **通过了检查就进行显示
     ********************************************/
    private void handle(String key)
    {
        //进行除=和AC外的输入时
        //进行当前的输入检查，通过继续输入标志控制当前的输入是否有效
        if(list.isEmpty())//检查表达式的第一位
            //list中为空时，前一位使用#辅助检查
            check("#",key);
        else
            //list.get(list.size()-1）获取当前输入的前一位
            check(list.get(list.size()-1),key);//通过前一位检查新的输入是否合法

        //如果通过了检查就加入list中，同时在屏幕就当前输入进行显示
        //输入正确加入list
        if (isContinueInput &&"0123456789.()+-*/".indexOf(key) != -1) {
            list.add(key);
        }
        //输入正确就把当前的输入进行显示，通过iscontinue的状态控制条件
        if (isContinueInput && "0123456789.()+-*/".indexOf(key) != -1) {
            if (equals_flag == false && ("+-*/".indexOf(key) != -1)) {
                vbegin = false;
                equals_flag = true;
                printText(key);
            } else if (equals_flag == false
                    && ("0123456789.()".indexOf(key) != -1)) {
                vbegin = true;
                equals_flag = true;
                printText(key);
            } else {
                printText(key);
            }
        }
        //进行=输入时，iscontinue修改为false，equal_flag改为false，vbegin改为true
        else if (isContinueInput && equals_flag && key.equals("=")) {
            isContinueInput = false;// 表明不可以继续输入
            equals_flag = false;// 表明已经输入=
            vbegin = true;// 重新输入标志设置true
            //点了等号就调用计算函数，并且清空list
            calculate(resultText.getText()); // 整个程序的核心，计算表达式的值并显示
            list.clear();
        }
        isContinueInput = true;
    }


    /*****************************************************
     **函数名及参数：check(String command1,String command2)
     **返回类型：void
     **作用：输入的检查函数，其中详细的分为第一位检查，小数点重复检查
     * **如果某一项没有通过检查就将控制标志isContinueInput设置为false，表示不能继续输入当前的选择
     *********************************************************/
    private void check(String command1,String command2)
    {
        //command2为当前想要进行的输入，command1为他的前一位
        boolean input_check,point_check,barket_check;
        input_check=Input_check(command1,command2);
        point_check=Point_check(command1,command2);
        barket_check=Barket_check(command1,command2);
        //未通过输入检查，小数点检查或者括号检查时就不能继续输入
        //if表达式为true说明没有通过检查，就设置继续输入标志为false表示不能继续输入
        if(!(input_check&&point_check&&barket_check))
            isContinueInput=false;
    }


    /***********************************************************
     **函数名及参数：input_check(String command1,String command2)
     **返回类型： boolean
     **作用:用于检查输入的第一位和输入时相邻两位是否合法，该方法由check()方法调用
     *****************************************************************/
    private boolean Input_check(String command1,String command2)
    {
        boolean flag=true;//返回标志初始化为true，表示可以继续输入

        //检查第一位输入是否合法，第一位的输入不能为 + * / .，此时假设第一位的前一位为#
        if(command1.equals("#")&&(command2.equals("+") ||command2.equals("*")
                ||command2.equals("/") || command2.equals(".")||command2.equals(")"))){
            flag=false;
        }

        //检查相邻两位的不合法的情况
        //左括号后面不能直接出现）+*/
        if(command1.equals("(")&&(command2.equals(")")||command2.equals("+")||
                command2.equals("*")||command2.equals("/")||command2.equals("."))){
            flag=false;
        }
        //右括号后面不能直接出现（ .数字
        if(command1.equals(")")&&(command2.equals("(")||command2.equals(".")||
                ("012345678".indexOf(command2)!=-1))){
            flag=false;
        }
        //小数点后面不能直接出现左括号且不能同时有两个小数点
        if(command1.equals(".")&&(command2.equals("(")||command2.equals("."))){
            flag=false;
        }
        //数字后面不能直接接左括号
        if(("0123456789".indexOf(command1)!=-1)&&command2.equals("(")){
            flag=false;
        }
        //+-*/后面不能直接+-*/和）
        if(("+-*/".indexOf(command1)!=-1)&&(("+-*/".indexOf(command2)!=-1)||command2.equals(")"))){
            flag=false;
        }
        return flag;
    }


    /**************************************************************
     **函数名及参数：point_check(String command1,String command2)
     **返回类型： boolean
     **作用:用于检查小数点的重复性，如果有重复就返回false
     **************************************************************/
    private boolean Point_check(String command1,String command2)
    {
        //当前想要输入为.时，就要检查正在输入的数字中是否已经出现了.
        int point = 0;//进行小数点计数
        boolean flag = true;//用于返回的检查标志

        if(command2.equals(".")) {
            //计数到目前输入为止出现的小数点数,一旦遇到符号就将计数清零
            for (int i = 0; i < list.size(); i++) {
                // 若之前出现一个小数点点，则小数点计数加1
                if (list.get(i).equals(".")) {
                    point++;
                }

                //list第i为一旦遇到+-*/()就将小数点计数清零
                if (list.get(i).equals("+") || list.get(i).equals("-") || list.get(i).equals("*") ||
                        list.get(i).equals("/") || list.get(i).equals(")"))
                    point = 0;
            }
            point++;
            //通过计数后如果小数点的个数仍然>1就将标志设为false
            if (point > 1)
                flag = false;
        }
        return flag;
    }


    /**************************************************************
     **函数名及参数：barket_check(String command1,String command2)
     **返回类型： boolean
     **作用:括号的匹配检查，确保点击=之前的所有括号时匹配的，同时不会单独出现）
     **************************************************************/
    private boolean Barket_check(String command1,String command2){
        boolean flag=true;//标志初始化为true

        // 检测右括号是否匹配，通过右边括号计数，进行循环计数
        //command2为）时
        if ( command2.compareTo(")") == 0) {
            int tip_right_bracket = 0;//计数前面初始化为0
            for (int i = 0; i < list.size(); i++) {
                // 如果出现一个左括号，则计数加1
                if (list.get(i).equals("(")) {
                    tip_right_bracket++;
                }
                // 如果出现一个右括号，则计数减1
                if (list.get(i).equals(")")) {
                    tip_right_bracket--;
                }
            }
            // 如果右括号计数=0,表明没有相应的左括号与当前右括号匹配
            if (tip_right_bracket == 0) {
                flag= false;
            }
        }

        // 检查输入=的合法性
        if (command2.compareTo("=") == 0) {
            // 括号匹配数
            int tip_bracket = 0;//遇到（就++,遇到）就--
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals("(")) {
                    tip_bracket++;
                }
                if (list.get(i).equals(")")) {
                    tip_bracket--;
                }
            }
            // 若大于0，表明左括号还有未匹配的
            if (tip_bracket > 0)
            {
                flag= false;
            }
            else if (tip_bracket == 0) {
                // 等号的前面如果出现了+-*/，表明=号不合法
                if ("+-*/".indexOf(command1) != -1) {
                    flag= false;
                }
            }
        }
        return flag;
    }



    /******************************************************
     **函数名及参数： printText(String key)
     **返回类型： void
     **作用: 将通过了检查的进行显示
     *****************************************************/
    private void printText(String key)
    {
        //vbegin为true时直接设置文本框输入key,else就之前的显示进行添加
        if (vbegin) {
            resultText.setText(key);
        } else {
            resultText.setText(resultText.getText() + key);
        }
        vbegin = false;
    }



    /*****************************************************
     **函数名及参数： printText(String key)
     **返回类型： void
     **作用: 进行计算，并显示结果
     ******************************************************/
    private void calculate(String expression)
    {
        //计算文本框的表达式，设置结果
        Calculate.cal(expression);
        resultText.setText(Calculate.cal(expression)+"");
    }




}