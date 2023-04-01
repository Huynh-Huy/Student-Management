/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package qlsv;

import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.util.Calendar;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class QLSinhVien extends javax.swing.JFrame {
    Connection cons;
    /**
     * Creates new form QLSinhVien
     */
    public QLSinhVien() {
        initComponents();
        //big.setVisible(true);
    }
    public void init(){
        DefaultComboBoxModel modelNH = new DefaultComboBoxModel();
        modelNH.addElement("2019-2020");
        modelNH.addElement("2020-2021");
        modelNH.addElement("2021-2022");
        cbnamhoc.setModel(modelNH);
        
        DefaultComboBoxModel modelHK = new DefaultComboBoxModel();
        modelHK.addElement("1");
        modelHK.addElement("2");
        modelHK.addElement("hè");
        cbhocky.setModel(modelHK);
    }
    public String diemchu(float diem){
        if (diem >= 9) return "A";
        else if (diem >=8) return "B+";
        else if (diem >=7) return "B";
        else if (diem >=6.5) return "C+";
        else if (diem >=5.5) return "C";
        else if (diem >=5) return "D+";
        else if (diem >=4) return "D";
        else return "F";
    }
    public float diemtrungbinh(int n, String diem[]){
        float[] thang4 = new float[n];
        int i=0;
        float tong=0.0f;
        for(i=0;i<n;i++){
            if (diem[i].equals("A")) thang4[i]=4.0f;
            else if (diem[i].equals("B+")) thang4[i]=3.5f;
            else if (diem[i].equals("B")) thang4[i]=3.0f;
            else if (diem[i].equals("C+")) thang4[i]=2.5f;
            else if (diem[i].equals("C")) thang4[i]=2.0f;
            else if (diem[i].equals("D+")) thang4[i]=1.5f;
            else if (diem[i].equals("D")) thang4[i]=1.0f;
            else if (diem[i].equals("F")) thang4[i]=0.0f;
            tong+=thang4[i];
        }
        return tong/n;
    }
    public String xeploai(float diem){
        if (diem>=3.6) return "Xuất sắc";
        else if (diem>=3.2) return "Giỏi";
        else if (diem>=2.5) return "Khá";
        else if (diem>=2.0) return "Trung bình";
        else if (diem>=1.0) return "Trung bình yếu";
        else return "Kém";
    }
    void xuatbangdiem(){
        Connection cons = DBConnect.getConnection();
        try{
            String file = "C:\\Users\\Tan Huy\\Desktop\\LTJV\\B1906674\\src\\qlsv\\in_bang_diem.txt";
            PrintWriter out = new PrintWriter(file);
            String mssv = txtmssv.getText();
            Statement s = cons.createStatement();
            Statement s2 = cons.createStatement();
            ResultSet rs;
            ResultSet rs2;
            rs = s.executeQuery("SELECT * FROM SINHVIENN WHERE mssv='"+mssv+"'");
            if (rs.isBeforeFirst()){
                rs.next();
                
                String namhoc = (String)cbnamhoc.getSelectedItem();
                String hocky = (String)cbhocky.getSelectedItem();
                out.println("");
                out.println("  BẢNG ĐIỂM SINH VIÊN TRƯỜNG ĐẠI HỌC CẦN THƠ");
                out.println("\t\t\t\t\t\tMẫu In:");
                out.println("________________________________________________");
                out.println(String.format("| Họ và tên        : %s      |",rs.getString(2)));
                out.println(String.format("| Mã số sinh viên  : %s                |",rs.getString(1)));
                out.println(String.format("| Lớp              : %s                |",rs.getString(3)));
                out.println(String.format("| Ngành            : %s      |",rs.getString(4)));
                out.println(String.format("| Khoa             : %s      |",rs.getString(5)));
                out.println(String.format("| Giới tính        : %s                |",rs.getString(7)));
                out.println(String.format("| Quê Quán         : %s                |",rs.getString(6)));
                out.println("|______________________________________________|");
                out.println("");
                out.println("");
                out.println(String.format("Học Kỳ %s - Năm Học %s",hocky,namhoc));
                out.println("");
                out.println("____________________________________________________________________________________");
                out.println(String.format("|Mã HP     Tên học phần                 Nhóm      Tín Chỉ    Điểm Thi    Quy Đổi   |",hocky,namhoc));
                out.println("|__________________________________________________________________________________|");
                rs = s.executeQuery("SELECT mshp,nhom,diem FROM HOC WHERE mssv='"+mssv+"' AND hocky='"+hocky+"' AND namhoc='"+namhoc+"'");
                if (rs.isBeforeFirst()){
                    while(rs.next()){
                        rs2 = s2.executeQuery("SELECT * FROM HOCPHAN WHERE mshp='"+rs.getString(1)+"'");
                        rs2.next();
                        float diem = Float.parseFloat(rs.getString(3));
                        out.println(String.format("|%s%s     %s  %s\t\t %s %s      |",rs2.getString(1),rs2.getString(2),rs.getString(2),rs2.getString(3),rs.getString(3),diemchu(diem)));
                        
                    }
                }
                out.println("|__________________________________________________________________________________|");
                int ngay = Calendar.getInstance().get(Calendar.DATE);
                int thang = Calendar.getInstance().get(Calendar.MONTH);
                int nam = Calendar.getInstance().get(Calendar.YEAR);
                out.println("");
                out.println(String.format("Ghi chú + Điểm trung bình được phân loại như sau:\n" +
                "           Loại Xuất Sắc Từ 3.6 đến 4.00           Loại Khá	     Từ 2.5 đến 3.19\n" +
                "           Loại Giỏi     Từ 3.2 đến 3.59           Loại Trung bình   Từ 2.0 đến 2.49\n\n" +
                "                                     Cần Thơ, Ngày %d Tháng %d Năm %d",ngay,thang+1,nam));
                out.close();
                JOptionPane.showMessageDialog(this,"Xuất bản điểm thành công!","Thông báo",JOptionPane.INFORMATION_MESSAGE);           
            }
            else JOptionPane.showMessageDialog(this,"Dữ liệu không tồn tại","Lỗi!",JOptionPane.ERROR_MESSAGE);           
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Dữ liệu không tồn tại","Lỗi!",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
    }
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        big = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtmssv = new javax.swing.JTextField();
        txtdiem = new javax.swing.JTextField();
        cbnamhoc = new javax.swing.JComboBox<>();
        cbhocky = new javax.swing.JComboBox<>();
        cbmshp = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txthovaten = new javax.swing.JTextField();
        txtlop = new javax.swing.JTextField();
        txtnganh = new javax.swing.JTextField();
        txtquequan = new javax.swing.JTextField();
        txtkhoa = new javax.swing.JTextField();
        txtgioitinh = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        tbcapnhat = new javax.swing.JButton();
        btquayvetrangtruoc = new javax.swing.JButton();
        btlammoi = new javax.swing.JButton();
        btxuatbangdiem = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbdiem = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtdtb = new javax.swing.JLabel();
        txtxeploai = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản Lý Sinh Viên");
        setResizable(false);

        big.setBackground(new java.awt.Color(0, 204, 204));

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("MÃ SỐ SINH VIÊN");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("NĂM HỌC");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("HỌC KỲ");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("MÃ SỐ HỌC PHẦN");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("ĐIỂM SỐ");

        txtmssv.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtdiem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        cbnamhoc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbnamhoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2019-2020", "2020-2021", "2021-2022" }));

        cbhocky.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbhocky.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "hè" }));

        cbmshp.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbmshp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CT173", "CT112", "CT113", "CT177", "KN001", "ML018", "TN010", "CT101", "TN012", "KT119", "KT440", "KT133" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbmshp, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtdiem, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbnamhoc, 0, 184, Short.MAX_VALUE)
                    .addComponent(cbhocky, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtmssv))
                .addGap(29, 29, 29))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtmssv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbnamhoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbhocky, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbmshp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtdiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("HỌ VÀ TÊN");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("LỚP ");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("NGÀNH");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("GIỚI TÍNH");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("QUÊ QUÁN");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("KHOA");

        txthovaten.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        txtlop.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        txtnganh.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        txtquequan.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        txtkhoa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        txtgioitinh.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel14))
                                .addGap(52, 52, 52)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtlop)
                                    .addComponent(txtnganh)
                                    .addComponent(txtkhoa)
                                    .addComponent(txtquequan)
                                    .addComponent(txtgioitinh)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel9)
                                .addGap(42, 42, 42)
                                .addComponent(txthovaten, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txthovaten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtlop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtkhoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtquequan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtgioitinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setText("QUẢN LÝ ĐIỂM SINH VIÊN");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("THÔNG TIN SINH VIÊN");

        jPanel3.setBackground(new java.awt.Color(51, 255, 102));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("HỆ THỐNG QUẢN LÝ ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(204, 255, 255));

        tbcapnhat.setText("CẬP NHẬT");
        tbcapnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbcapnhatActionPerformed(evt);
            }
        });

        btquayvetrangtruoc.setText("TRỞ VỀ");
        btquayvetrangtruoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btquayvetrangtruocActionPerformed(evt);
            }
        });

        btlammoi.setText("TÌM");
        btlammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btlammoiActionPerformed(evt);
            }
        });

        btxuatbangdiem.setText("XUẤT BẢNG ĐIỂM");
        btxuatbangdiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btxuatbangdiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(tbcapnhat)
                .addGap(76, 76, 76)
                .addComponent(btlammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(btxuatbangdiem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btquayvetrangtruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbcapnhat)
                    .addComponent(btquayvetrangtruoc)
                    .addComponent(btlammoi)
                    .addComponent(btxuatbangdiem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbdiem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbdiem.setAutoscrolls(false);
        jScrollPane1.setViewportView(tbdiem);

        jPanel5.setBackground(new java.awt.Color(204, 255, 255));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("ĐIỂM TRUNG BÌNH : ");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("XẾP LOẠI :");

        txtdtb.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtxeploai.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtdtb, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(txtxeploai, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtxeploai, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtdtb, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(jLabel15)))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(51, 255, 102));

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 11)); // NOI18N
        jLabel19.setText("LẬP TRÌNH JAVA CT276 _HUỲNH TẤN HUY B1906674");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(194, 194, 194))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout bigLayout = new javax.swing.GroupLayout(big);
        big.setLayout(bigLayout);
        bigLayout.setHorizontalGroup(
            bigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bigLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(bigLayout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        bigLayout.setVerticalGroup(
            bigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bigLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(bigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(big, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(big, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btquayvetrangtruocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btquayvetrangtruocActionPerformed
        // TODO add your handling code here:
        menu mn = new menu();
        mn.show();
        dispose();
    }//GEN-LAST:event_btquayvetrangtruocActionPerformed

    private void btlammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btlammoiActionPerformed
        // TODO add your handling code here:
        Connection cons = DBConnect.getConnection();//THỰC HIỆN MỘT KẾT NỐI VỚI CSDL GỌI PHƯƠNG THỨC GETCONNECTION( DỂ NHẬN VỀ ĐỐI TƯỢNG CONNECTION
        try{
            String mssv=txtmssv.getText();
            String namhoc=(String)cbnamhoc.getSelectedItem();
            String hocky=(String)cbhocky.getSelectedItem();
            String hocphan = (String)cbmshp.getSelectedItem();
            Statement s = cons.createStatement();//TẠO THỰC THI câu lệnh sql nhờ đối tượng statement (createStatement()) là phương t của dt connect
            ResultSet rs; // đối tượng ResultSet chứa tập kết quả trả về khi thực hiện câu truy vấn
            rs = s.executeQuery("SELECT SINHVIENN.hoten,SINHVIENN.lop,SINHVIENN.nganh,SINHVIENN.khoa,SINHVIENN.quequan,SINHVIENN.gioitinh FROM [SINHVIENN]\n" 
                                +"WHERE mssv='"+mssv+"'");//executeQuery truy xuất thông tin từ CSDL trả về tập kết quả ResultSet 
            if (rs.isBeforeFirst()==true){//isBeforeFirst di chuyển con trỏ trc mẫu tin đầu tiên
                while(rs.next()){//di chuyển con trỏ sang dòng kế tiếp
                txthovaten.setText(rs.getString(1));
                txtlop.setText(rs.getString(2));
                txtnganh.setText(rs.getString(3));
                txtkhoa.setText(rs.getString(4));
                txtquequan.setText(rs.getString(5));
                txtgioitinh.setText(rs.getString(6));
                }
            }
            else{
                txthovaten.setText("");
                txtlop.setText("");
                txtnganh.setText("");
                txtkhoa.setText("");
                txtquequan.setText("");
                txtgioitinh.setText("");
            }
            // thông tin sinh viên và table điểm
            rs = s.executeQuery("SELECT DISTINCT HOCPHAN.mshp,HOCPHAN.tenhp,HOC.diem FROM HOCPHAN\n" +
                                            "INNER JOIN HOC ON HOCPHAN.mshp=HOC.mshp \n" +
                                            "WHERE HOC.mssv='"+mssv+"' and HOC.namhoc='"+namhoc+"' and HOC.hocky='"+hocky+"'");
            DefaultTableModel m = new DefaultTableModel(new Object[]{"STT","Mã học phần","Tên học phần","Điểm số","Điểm chữ"},0);
            tbdiem.setModel(m);
            int stt=0;
            String []diem = new String[25]; 
            while (rs.next()){
                    stt++;
                    ((DefaultTableModel)tbdiem.getModel()).addRow(new Object[]{stt,rs.getString(1),rs.getString(2),rs.getFloat(3),diemchu(rs.getFloat(3))}); 
                    diem[stt-1] = new String();
                    diem[stt-1] = diemchu(rs.getFloat(3));
                }
            
            // tính điểm trung bình
            rs = s.executeQuery("SELECT mshp FROM [HOC]" + 
                                "WHERE mssv='"+mssv+"'" +
                                "AND namhoc='"+namhoc+"'" +
                                "AND hocky='"+hocky+"'");
            DefaultComboBoxModel model = new DefaultComboBoxModel();
            while(rs.next()){
                model.addElement(rs.getString(1));
            }
            cbmshp.setModel(model);
            if (stt!=0){
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String dtb = decimalFormat.format(diemtrungbinh(stt,diem));
                txtdtb.setText(dtb);
                txtxeploai.setText(xeploai(diemtrungbinh(stt,diem)));
            }
            else{
                txtdtb.setText("");
                txtxeploai.setText("");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(this,"Dữ liệu không hợp lệ","Lỗi!",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            
        }
    }//GEN-LAST:event_btlammoiActionPerformed

    private void tbcapnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbcapnhatActionPerformed
        //cập nhật lại điểm mới
        Connection cons = DBConnect.getConnection();
        try{
            Statement s = cons.createStatement();
            ResultSet rs;
            String mssv=txtmssv.getText();
            String namhoc=(String)cbnamhoc.getSelectedItem();
            String hocky=(String)cbhocky.getSelectedItem();
            String diem = txtdiem.getText();
            String hocphan = (String)cbmshp.getSelectedItem();
            String sql = "UPDATE HOC\n" +
                                "SET HOC.diem='"+diem+"'\n" +
                                "WHERE HOC.mssv='"+mssv+"'\n" +
                                "AND HOC.namhoc='"+namhoc+"'\n" +
                                "AND HOC.hocky='"+hocky+"'\n" +
                                "AND HOC.mshp='"+hocphan+"'";
            PreparedStatement s2 = cons.prepareStatement(sql);
            
            s2.executeUpdate();
            if (s2.executeUpdate()==1){
                
                rs = s.executeQuery("SELECT DISTINCT HOCPHAN.mshp,HOCPHAN.tenhp,HOC.diem FROM HOCPHAN\n" +
                                                "INNER JOIN HOC ON HOCPHAN.mshp=HOC.mshp \n" +
                                                "WHERE HOC.mssv='"+mssv+"' and HOC.namhoc='"+namhoc+"' and HOC.hocky='"+hocky+"'");

                DefaultTableModel m = new DefaultTableModel(new Object[]{"STT","Mã học phần","Tên học phần","Điểm số","Điểm chữ"},0);
                tbdiem.setModel(m);
                int stt=0;
                String []dsdiem = new String[25]; 
                while (rs.next()){
                        stt++;
                        ((DefaultTableModel)tbdiem.getModel()).addRow(new Object[]{stt,rs.getString(1),rs.getString(2),rs.getFloat(3),diemchu(rs.getFloat(3))});                    
                        dsdiem[stt-1] = new String();
                        dsdiem[stt-1] = diemchu(rs.getFloat(3));
                }
                if (stt!=0){
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String dtb = decimalFormat.format(diemtrungbinh(stt,dsdiem));
                txtdtb.setText(dtb);
                txtxeploai.setText(xeploai(diemtrungbinh(stt,dsdiem)));
                JOptionPane.showMessageDialog(this,"Cập nhật thành công!","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            }
            }
            else JOptionPane.showMessageDialog(this,"Dữ liệu không hợp lệ","Lỗi!",JOptionPane.ERROR_MESSAGE);
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Dữ liệu không hợp lệ","Lỗi!",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_tbcapnhatActionPerformed

    private void btxuatbangdiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btxuatbangdiemActionPerformed
        // TODO add your handling code here:
        xuatbangdiem();
    }//GEN-LAST:event_btxuatbangdiemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QLSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLSinhVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel big;
    private javax.swing.JButton btlammoi;
    private javax.swing.JButton btquayvetrangtruoc;
    private javax.swing.JButton btxuatbangdiem;
    private javax.swing.JComboBox<String> cbhocky;
    private javax.swing.JComboBox<String> cbmshp;
    private javax.swing.JComboBox<String> cbnamhoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton tbcapnhat;
    private javax.swing.JTable tbdiem;
    private javax.swing.JTextField txtdiem;
    private javax.swing.JLabel txtdtb;
    private javax.swing.JTextField txtgioitinh;
    private javax.swing.JTextField txthovaten;
    private javax.swing.JTextField txtkhoa;
    private javax.swing.JTextField txtlop;
    private javax.swing.JTextField txtmssv;
    private javax.swing.JTextField txtnganh;
    private javax.swing.JTextField txtquequan;
    private javax.swing.JLabel txtxeploai;
    // End of variables declaration//GEN-END:variables
}
