

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



// 完成一个mini版本的学生管理系统

public class StudentManagement extends JFrame implements ActionListener {

//    定义控件
    JPanel jp1,jp2;
    JLabel jl;
    JButton jb1,jb2,jb3,jb4;
    JTable jt;
    JTextField jtf;
    JScrollPane jsp = null;
    StuModel sm = null;



    public static void main(String[] args) {
        StudentManagement studentManagement = new StudentManagement();
    }

//    构造函数
    public StudentManagement(){

//        创建标签
        jl = new JLabel("请输入姓名");
//        创建长度为10的单行文本框
        jtf = new JTextField(10);
//        创建查询按钮
        jb1 = new JButton("查询");
//        注册监听
        jb1.addActionListener(this);



//        将标签、单行文本框、查询按钮加入到jp1
        jp1 = new JPanel();
        jp1.add(jl);
        jp1.add(jtf);
        jp1.add(jb1);

        jp2 = new JPanel();
        jb2 = new JButton("添加");
        jb3 = new JButton("修改");
        jb4 = new JButton("删除");

//        注册监听按钮jb2、jb3、jb4
        jb2.addActionListener(this);
        jb3.addActionListener(this);
        jb4.addActionListener(this);

//        把按钮添加、修改、删除加入到jp2中
        jp2.add(jb2);
        jp2.add(jb3);
        jp2.add(jb4);

//        创建一个数据模型对象
        sm = new StuModel();
//        初始化JTable
        jt = new JTable(sm);
//        初始化jsp JScrollPane
        jsp = new JScrollPane(jt);

//        把jsp放入到JFrame
        this.add(jsp);

//        设置jp1,jp2的布局
        this.add(jp1, BorderLayout.SOUTH);
        this.add(jp2,BorderLayout.NORTH);
//        设置JFrame的标题
        this.setTitle("MIS");
//        设置JFrame的长宽尺寸
        this.setSize(500,500);
//         设置JFrame出现的起始位置
        this.setLocation(500,200);
//        如果没有该句，默认点“X”时，只是暂时隐藏窗体，在后台进程中还可以看到
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        设置JFrame的初始可见性
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent event){
//        判断是哪个按钮被点击
        if(event.getSource() == jb1){
            System.out.println("执行查询操作");
//            因为把对表的数据封装到StuModel中，我们就可以比较简单的完成查询
//            trim()把空格过滤一下
            String name = this.jtf.getText().trim();
//            写一个sql语句，用于将输入的值和数据库做对比
            String sql = "select * from student where stuName='"+name+"'";
//            构件新的数据模型类，并更新
            if(name.trim().equals("")){
                sm = new StuModel();
            }else{
                sm = new StuModel(sql);
            }
//            更新JTable
            jt.setModel(sm);
            //当用户点击添加
        }else if(event.getSource() == jb2){
            System.out.println("执行添加操作");
//            合理应该为模式的状态，否则，还没有插完就会执行下面的语句，导致无法更新
//            model为false状态，则表明也可以操作母窗口
//            model为true状态，则表明要先操作完子窗口，才能操作子窗口
            StuAddDialog sa = new StuAddDialog(this,"添加学生",true);
//            重新在获得新的数据模型
            sm = new StuModel();
            jt.setModel(sm);
        }else if(event.getSource() == jb3){
            System.out.println("执行修改操作");
            int rownum = this.jt.getSelectedRow();
            if(rownum == -1){
//                提示
                JOptionPane.showMessageDialog(this,"请选择一行");
//                代表不要再往下面走，谁调用就返回给水
                return;
            }
//           显示修改对话框
            new StudentUpdateDialog(this,"修改对话框",true,sm,rownum);
        }else if(event.getSource() == jb4){
            System.out.println("执行删除操作");
//            得到该学生的id
//            getSelectedRow会返回用户点击的行
//            如果一行都没选，则会返回-1
            int rownum = this.jt.getSelectedRow();
            if(rownum == -1){
//                提示
                JOptionPane.showMessageDialog(this,"请选择一行");
//                代表不要再往下面走，谁调用就返回给水
                return;
            }
//            得到学生的编号
            String stuId = (String)sm.getValueAt(rownum,0);
//            System.out.print(stuId);测试用
            StuModel temp = new StuModel();
//            创建一个sql语句
            String sql = "delete from student where stuId=?";
            String[] paras = {stuId};
            if(!temp.updateStudent(sql,paras)){
//                提示
                JOptionPane.showMessageDialog(this,"删除失败");
            }
//            解决一次无用的查询
            sm = new StuModel();
            jt.setModel(sm);
        }
    }
}
