

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//修改学生界面


public class StudentUpdateDialog extends JDialog implements ActionListener{
//    定义需要的swing组件
    JLabel jl1,jl2,jl3,jl4,jl5,jl6;
    JButton jb1,jb2;
    JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6;
    JPanel jp1,jp2,jp3;

    //    构造函数，Frame代表父窗口，title代表窗口名字，model指的是是否为模式窗口
    public StudentUpdateDialog(Frame owner,String title,boolean model,StuModel sm,int rownum){
//        调用父类构造方法，达到模式对话框效果
        super(owner,title,model);

        jl1 = new JLabel("学号");
        jl2 = new JLabel("姓名");
        jl3 = new JLabel("性别");
        jl4 = new JLabel("年龄");
        jl5 = new JLabel("籍贯");
        jl6 = new JLabel("系别");

        jtf1 = new JTextField();
        jtf2 = new JTextField();
        jtf3 = new JTextField();
        jtf4 = new JTextField();
        jtf5 = new JTextField();
        jtf6 = new JTextField();

//        初始化数据
        jtf1.setText((String)sm.getValueAt(rownum,0));

//        setEditable设置指定的boolean变量，以指示此文本空间，是否应该为可编辑的
        jtf1.setEditable(false);

        jtf2.setText((String)sm.getValueAt(rownum,1));

        jtf3.setText((String)sm.getValueAt(rownum,2));

        jtf4.setText(sm.getValueAt(rownum,3)+"");

        jtf5.setText((String)sm.getValueAt(rownum,4));

        jtf6.setText((String)sm.getValueAt(rownum,5));

        jb1 = new JButton("修改");
        jb2 = new JButton("取消");

        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();

//        设置布局
        jp1.setLayout(new GridLayout(6,1));
        jp2.setLayout(new GridLayout(6,1));

//        添加组件
        jp1.add(jl1);
        jp1.add(jl2);
        jp1.add(jl3);
        jp1.add(jl4);
        jp1.add(jl5);
        jp1.add(jl6);

        jp2.add(jtf1);
        jp2.add(jtf2);
        jp2.add(jtf3);
        jp2.add(jtf4);
        jp2.add(jtf5);
        jp2.add(jtf6);

        jp3.add(jb1);
        jp3.add(jb2);

        this.add(jp1,BorderLayout.WEST);
        this.add(jp2,BorderLayout.CENTER);
        this.add(jp3,BorderLayout.SOUTH);

//        注册监听
        jb1.addActionListener(this);
        jb2.addActionListener(this);

//        展现
        this.setSize(300,200);
        this.setLocation(200,300);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jb1){

            String sql = "update student set stuName=?,stuSex=?,stuAge=?,stuHome=?,stuDept=? where stuId=?";
            String[] paras = {this.jtf2.getText(),this.jtf3.getText(),this.jtf4.getText(),
                    this.jtf5.getText(),this.jtf6.getText(),this.jtf1.getText(),};
            StuModel temp = new StuModel();
            temp.updateStudent(sql,paras);
        }else if(e.getSource() == jb2){
            dispose();
        }
    }
}
