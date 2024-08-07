package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

/*
 * File Name	:ProjectInfo.java
 * Version		:Ver1.0
 * Designer		:荻野新
 * Date			:2024.06.16
 * Purpose		:企画の情報を扱うためのクラス
 * 
 * set
 * ProjectInfo project = new ProjectInfo();
 * project.projectID = ....
 * .
 * .
 * .
 * .
 * .
 * project.setProjectInfo();
 * 
 * 
 * get
 * ProjectInfo cons = new ProjectInfo();
 * ProjectInfo project = cons.getProjectInfo(projectID);
 * 
 */

public class ProjectInfo {
    public int projectID;
    public String projectName = "";
    public Timestamp dateTime;
    public String category = "";
    public String destination = "";
    public int managerID;
    public String region = "";
    public String progressStatus = "";

    // DB接続のためのアドレスなど
    String server = "//172.18.80.1:5432/"; // seserverのIPアドレス
    String dataBase = "test1";
    String user = "oops";
    String passWord = "pass";
    String url = "jdbc:postgresql:" + server + dataBase;


	// 企画IDから企画情報を取得、projectinfoクラスのフィールドに保存するメソッド
    public ProjectInfo getProjectInfo(int projectID) {

        // DB接続
        try {
            ProjectInfo ret = new ProjectInfo();

            Class.forName("org.postgresql.Driver");

            Connection con = DriverManager.getConnection(url, user, passWord);
            Statement stmt = con.createStatement();
            // 検索の実施と結果の格納
            String sql = "SELECT * FROM ProjectsTableNinth WHERE ProjectID = " + projectID;
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            ret.projectID = rs.getInt(projectID);
            ret.projectName = rs.getString("Name");
            ret.dateTime = rs.getTimestamp("DateTime");
            ret.category = rs.getString("Category");
            ret.destination = rs.getString("Destination");
            ret.managerID = rs.getInt("ManagerID");
            ret.region = rs.getString("Region");
            ret.progressStatus = rs.getString("ProgressStatus");

            stmt.close();
            con.close();

            return ret;
        } catch (Exception e) {
            ProjectInfo ret = new ProjectInfo();
            e.printStackTrace();
            return ret;
        }
    }

  //ProjectInfoクラスのフィールドに格納されたデータをデータベースに登録するメソッド
    public void setProjectInfo() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url, user, passWord);

            String sql = "INSERT INTO ProjectsTableNinth VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prestmt = con.prepareStatement(sql);

            prestmt.setInt(1, projectID);
            prestmt.setString(2, projectName);
            prestmt.setTimestamp(3, dateTime);
            prestmt.setString(4, category);
            prestmt.setString(5, destination);
            prestmt.setInt(6, managerID);
            prestmt.setString(7, region);
            prestmt.setString(8, progressStatus);

            prestmt.executeUpdate();
            prestmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
