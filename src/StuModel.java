

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.Vector;

/*
* 此处是student表的模型
* */
public class StuModel extends AbstractTableModel{

//    rowData用来存放行数据,columnNames存放列数据
    Vector rowData,columnNames;
    JTable jt = null;

    public PreparedStatement ps = null;
    public Connection conn= null;
    public ResultSet rs = null;

    public static final String url = "jdbc:mysql://localhost:3306/studentmanage";
    public static final String driver = "com.mysql.jdbc.Driver";
    public static final String user = "root";
    public static final String password = "111111";

//    添加，删除，修改学生 由于添加的参数不确定，因此用数组来传递参数
    public boolean updateStudent(String sql,String[] paras){
        boolean b = true;
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url,user,password);
            ps = conn.prepareStatement(sql);
            for(int i = 0; i < paras.length; i++){
                ps.setString(i + 1,paras[i]);
            }
            if(ps.executeUpdate() != 1){
                b = false;
            }
        }catch (Exception e){
            b = false;
            e.printStackTrace();
        }finally {
            try{
                if(ps != null) ps.close();
                if(conn != null)conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return b;
    }
//    做一个构造函数，用于初始化我们的数据模型
    public StuModel(){
        this.init("");
    }

//    通过传递sql语句来获得数据模型
    public StuModel(String sql){
        this.init(sql);
    }

    public void init(String sql){

        if(sql.trim().equals("")){
            sql = "select * from student";
        }

        jt = new JTable();
//        中间显示数据结果
//        存放列名字段
        columnNames = new Vector();
//        设置列名
        columnNames.add("学号");
        columnNames.add("名字");
        columnNames.add("性别");
        columnNames.add("年龄");
        columnNames.add("籍贯");
        columnNames.add("系别");

//        存放行数据
        rowData = new Vector();

        try{
//            1、注册mysql数据库的驱动
//            Class.forName(driver);
//            2、获取连接
            conn = DriverManager.getConnection(url,user,password);
            ps = conn.prepareStatement(sql);
//          得到结果集
            rs = ps.executeQuery();
            while(rs.next()){
//              rowData可以存放多行
                Vector hang = new Vector();
                hang.add(rs.getString(1));
                hang.add(rs.getString(2));
                hang.add(rs.getString(3));
                hang.add(rs.getInt(4));
                hang.add(rs.getString(5));
                hang.add(rs.getString(6));

//              加入到rowData
                rowData.add(hang);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{//关闭
            try{
                if(rs != null) rs.close();
                if(ps != null) ps.close();
                if(conn != null)conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }




//  该方法会被类自动调用
    @Override
    public int getRowCount() {//得到共有多少列
        return this.rowData.size();
    }

//  该方法会被类自动调用
    @Override
    public int getColumnCount() {//得到共有多少行
        return this.columnNames.size();
    }

//    不理解
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {//得到某行某列的数据
        return ((Vector)this.rowData.get(rowIndex)).get(columnIndex);
    }

    @Override
    public String getColumnName(int column) {
//        列名可以认为是String类型
        return (String)this.columnNames.get(column);
    }
}
