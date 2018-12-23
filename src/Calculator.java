import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author: Yan_Daojiang
 * @date: 2018/12/14
 * @description:
 **/
public class Calculator extends JFrame implements ActionListener {
    //需要进行运算的表达式
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
    private JTextField resultText = new JTextField("0");
    //设置两个panel用于添加按钮和文本框
    static JPanel key_pan = new JPanel();
    static JPanel result_pan = new JPanel();

    //控制标志
    private boolean vbegin = true;// 控制输入，true为重新输入，false为接着输入
    private boolean equals_flag = true;
    private boolean isContinueInput = true;// true为正确，可以继续输入，false错误，输入锁定

    final int MAXLEN = 500;


    //初始化
    public void go() {
        initLayout();//界面设置
        initAction();//监听
        list = new ArrayList<String>();
    }

    //基本布局设置
    public void initLayout() {
        //文本框的设置
        resultText.setHorizontalAlignment(JTextField.RIGHT);//设置对齐状态
        resultText.setEditable(false);//设置结果显示框为不可编辑状态
        resultText.setBackground(Color.YELLOW);
        //设置结果显示部分的布局并添加
        result_pan.setLayout(new BorderLayout());
        result_pan.add(resultText, BorderLayout.CENTER);

        //按钮部分的设置
        //设置运算按键的布局,添加按钮到key_pan
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

    //组件注册监听
    public void initAction(){
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

    //主函数
    public static void main(String[] args){
        Calculator mainJFrame=new Calculator();

        Container container=mainJFrame.getContentPane();
        //主界面的一些基本设置
        mainJFrame.setTitle("计算器1.0");
        mainJFrame.setSize(260,230);
        mainJFrame.setLocationRelativeTo(null);
        mainJFrame.setResizable(false);//设置窗口的大小不可改变
        mainJFrame.setDefaultCloseOperation(3);//设置关闭程序

        //设置主界面的布局，添加文本框和按钮的panel
        container.setLayout(new BorderLayout());
        container.add(result_pan,BorderLayout.NORTH);
        container.add(key_pan,BorderLayout.CENTER);

        //初始化
        mainJFrame.go();
        mainJFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //通过按钮上的标签获取用户点击的是哪个按钮
        String lable=e.getActionCommand();
        if(lable.equals("AC"))
            clear();
        else
            handle(lable);
    }

    //用于点击AC和需要将计算器清空的过程
    private void clear(){
        list.clear();
        resultText.setText("0");
        vbegin = true;//控制输入表示设为---重新输入
        equals_flag = true;
    }

    //处理点击AC之外的按钮，用于表达式的获取,输入一个检查一个
    private void handle(String key) {
        String text = resultText.getText();

        if(list.isEmpty())
            check("#",key);//检查表达式的第一位
        else
            check(list.get(list.size()-1),key);//通过前一位检查新的输入是否合法

        //如果通过了检查就加入list中，同时在品目就当前输入进行显示
        //输入正确加入list
        if (isContinueInput &&"0123456789.()+-*/".indexOf(key) != -1) {
            list.add(key);
        }
        //输入正确就进行显示
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
        //如果点击了=
        else if (isContinueInput && equals_flag && key.equals("=")) {
            isContinueInput = false;// 表明不可以继续输入
            equals_flag = false;// 表明已经输入=
            vbegin = true;// 重新输入标志设置true
            calculate(resultText.getText()); // 整个程序的核心，计算表达式的值并显示
            list.clear();
        }
        isContinueInput = true;
    }

    //检查输入，小数点和括号的匹配是否合法
    private void check(String command1,String command2){
        boolean input_check,point_check,barket_check;
        input_check=input_check(command1,command2);
        point_check=point_check(command1,command2);
        //未通过输入检查，小数点检查或者括号检查时就不能继续输入
        if(!(input_check&&point_check))
            isContinueInput=false;
    }

    //用于检查输入的第一位和输入时相邻两位是否合法，改该方法由check()方法调用
    private boolean input_check(String command1,String command2){
        boolean flag=true;
        //检查第一位输入是否合法，第一位的输入不能为 + * / .
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

    //用于检查小数点的重复性，如果有重复就返回false
    private boolean point_check(String command1,String command2) {
        int point = 0;//进行小数点计数
        boolean flag = true;//用于返回的检查标志

        if(command2.equals(".")) {
            //计数到目前输入为止出现的小数点数
            for (int i = 0; i < list.size(); i++) {
                // 若之前出现一个小数点点，则小数点计数加1
                if (list.get(i).equals(".")) {
                    point++;
                }

                //如果之前出现了小数点但同时出现了+-*/)就将计数清零,重新计数
                if (list.get(i).equals("+") || list.get(i).equals("-") || list.get(i).equals("*") ||
                        list.get(i).equals("/") || list.get(i).equals(")"))
                    point = 0;
            }
            point++;
            if (point > 1)
                flag = false;
        }
        return flag;
    }

    //显示
    private void printText(String key) {
        if (vbegin) {
            resultText.setText(key);// 清屏后输出
            // firstDigit = false;
        } else {
            resultText.setText(resultText.getText() + key);
        }
        vbegin = false;
    }



    //核心计算功能实现
    private void calculate(String expression) {
        System.out.println(expression );


    }

}
