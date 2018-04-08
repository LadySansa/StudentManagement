

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
* JDialogDemo，对话框，使用JDialog类可以创建自定义的对话框；
* */
public class StuAddDialog extends JDialog implements ActionListener{

//    定义需要的swing组件
    JLabel jl1,jl2,jl3,jl4,jl5,jl6;
    JButton jb1,jb2;
    JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6;
    JPanel jp1,jp2,jp3;


    /*
    * owner父窗口，title窗口名，model对话框是否为模态；
    * 对话框分为 模态 和 非模态：
    * 模态：弹出对话框后，对话框的父级窗口不可操作；
    * 非模态：弹出对话框后，对话框的父级窗口可以正常操作；
    * */
    public StuAddDialog(Frame owner,String title,boolean model){
//        调用父类构造方法，达到模式对话框效果
        super(owner,title,model);

//        子窗口的标签
        jl1 = new JLabel("学号");
        jl2 = new JLabel("姓名");
        jl3 = new JLabel("性别");
        jl4 = new JLabel("年龄");
        jl5 = new JLabel("籍贯");
        jl6 = new JLabel("系别");

//        子窗口的单行文本框
        jtf1 = new JTextField();
        jtf2 = new JTextField();
        jtf3 = new JTextField();
        jtf4 = new JTextField();
        jtf5 = new JTextField();
        jtf6 = new JTextField();

//        新建子窗口上的添加、取消按钮
        jb1 = new JButton("添加");
        jb2 = new JButton("取消");

/*
* JFrame是最底层，JPanel是置于其面上，同一个界面只有一个JFrame，一个JFrame可以放多个JPanel；
* 也可以直接在JFrame放置，但并不规范，过于复杂的界面并不好管理控件；
* JPanel方便进行控件分类管理；
* */
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();

//        设置jp1、jp2的布局
        jp1.setLayout(new GridLayout(6,1));
        jp2.setLayout(new GridLayout(6,1));

//        将jl1-jl6添加到jp1上
        jp1.add(jl1);
        jp1.add(jl2);
        jp1.add(jl3);
        jp1.add(jl4);
        jp1.add(jl5);
        jp1.add(jl6);

//        将jtf1-jtf6添加到jp2上
        jp2.add(jtf1);
        jp2.add(jtf2);
        jp2.add(jtf3);
        jp2.add(jtf4);
        jp2.add(jtf5);
        jp2.add(jtf6);

//        将jb1-jb2添加到jp3上
        jp3.add(jb1);
        jp3.add(jb2);

//        设置jp1、jp2、jp3的位置
        this.add(jp1,BorderLayout.WEST);
        this.add(jp2, BorderLayout.CENTER);
        this.add(jp3,BorderLayout.SOUTH);

        jb1.addActionListener(this);
        jb2.addActionListener(this);

//        展现
        this.setSize(300,200);
        this.setLocation(200,300);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == jb1){
            StuModel temp = new StuModel();
            String sql = "insert into student values(?,?,?,?,?,?)";
            String[] paras = {jtf1.getText(),jtf2.getText(),jtf3.getText()
                    ,jtf4.getText(),jtf5.getText() ,jtf6.getText()};
            if(!temp.updateStudent(sql,paras)){
                /*
                * JOptionPane是JavaSwing内部已经实现好的，以静态方法的形式提供调用，能够
                * 快速方便的弹出要求用户提供值或向其发出通知的标准对话框；
                * */
                JOptionPane.showMessageDialog(this,"添加失败");
            }
//            关闭对话框
            this.dispose();

        }else if(e.getSource() == jb2){
            this.dispose();
        }
    }


}
