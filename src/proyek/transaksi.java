/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyek;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import database.database;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.UIManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author dell
 */
public class transaksi extends javax.swing.JFrame {
    
    private Connection conn;
    private Statement st;
    private ResultSet rs;
    private DefaultTableModel model;
    
    JasperPrint JasPri;
    Map param=new HashMap();

    public ArrayList produk = new ArrayList();
    public ArrayList ukurann = new ArrayList();
    public ArrayList harga = new ArrayList();
    public ArrayList jumlah = new ArrayList();
    public ArrayList subtotal = new ArrayList();
    public ArrayList nofaktur = new ArrayList();
    public ArrayList id = new ArrayList();
    /**
     * 
     * Creates new form transaksi
     */
    public transaksi() {
        initComponents();
        nofaktur();
        tampilkan_data() ;
        total();
    }
    
    private void setHargaMinuman(){
    int harga=0;
    int pilihan = ukuran.getSelectedIndex();
    switch(pilihan){
        case 1:
            harga = 6000;
            break;
        case 2:
            harga = 11000;
            break;
        case 3:
            harga = 20000;
            break;
        case 4:
            harga = 29000;
            break;
        case 5: 
            harga = 0;
            break;
        case 6: 
            harga = 18000;
            break;
        case 7:
            harga = 10000;
            break;
        case 8:
            harga = 8000;
            break;
        }
        hargaminum.setText("" + harga);
    }
     private void reset_hapus(){
        prodminum.setSelectedItem("Pilih Minuman");
        ukuran.setSelectedItem("Pilih Ukuran");
        hargaminum.setText(null);
        jmlhminum.setText(null);
        asu.setText(null);
        subtotalminum.setText(null);        
   }
    
    
    private void setTabel(){
    int total=0;
    int bayar=0;
    
    DefaultTableModel tb = new DefaultTableModel();
    tb.addColumn("Produk");
    tb.addColumn("Ukuran");
    tb.addColumn("Harga");
    tb.addColumn("Jumlah");
    tb.addColumn("Sub Total");
    
    for(int n=0; n<produk.size(); n++){
        total = total + Integer.parseInt(subtotal.get(n).toString());
        
        tb.addRow(new Object[]{
        produk.get(n),
        ukurann.get(n),
        harga.get(n),
        jumlah.get(n),
        subtotal.get(n),
    });
    }
    bayar = (int)(total);
    keranjang.setModel(tb);
    asu.setText("" + bayar);
    
    }
    
    

    private boolean cekKosong(boolean minuman){
        boolean hasil = true;
        
        if(minuman == true){
            if(jmlhminum.getText().isEmpty() == true){
                JOptionPane.showMessageDialog(null, "Jumlah Minuman Harus Di isi ");
                hasil=true;
            }
        }else{
            if(jmlhminum.getText().isEmpty() == true){
                JOptionPane.showMessageDialog(null, "Jumlah Makanan Harus Di isi ");
                hasil=true;
        }
    }
        return false;
    }
    
    private boolean cekDataTabel(String data){
        boolean hasil = produk.contains(data);
        if(hasil==true) JOptionPane.showMessageDialog(null,"Menu Sudah Ditambahkan");
        return hasil;
    }
    
    private void refresh(){
    prodminum.setSelectedItem("Pilih Minuman");
    txtjmlhbayar.setText("");
    txtkembalian.setText("");
    asu.setText("");
        
        produk.clear();
        ukurann.clear();
        harga.clear();
        jumlah.clear();
        subtotal.clear();
        
        setTabel();
    }
     public void Simpan(){

try
{
Class.forName("com.mysql.jdbc.Driver").newInstance();
com.mysql.jdbc.Connection koneksi = (com.mysql.jdbc.Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1/proyek", "root", "");
com.mysql.jdbc.Statement statement = (com.mysql.jdbc.Statement) koneksi.createStatement();
String sql="insert into transaksi (nofaktur, nama, produk, ukuran, harga, jumlah_pesanan, totalharga, total, bayar, kembalian, tanggal)\n" +
"select distinct nofaktur,group_concat(nama),group_concat(produk),group_concat(ukuran),group_concat(harga),group_concat(jumlah_pesanan),group_concat(totalharga),group_concat(total),\n" +
"group_concat(bayar),group_concat(kembalian),group_concat(tanggal) from keranjang";
int executeUpdate = statement.executeUpdate(sql);
statement.close();

koneksi.close();
}
catch (     ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException | HeadlessException e)
{

//System.err.println("Exception: "+e.getMessage());
}
    }
private void simpan2(){
       String produk = (String) prodminum.getSelectedItem();
       String U = (String) ukuran.getSelectedItem();
       String harga = hargaminum.getText();
       String nofak=txtnofaktur.getText();
       String tot=asu.getText();
       String by=txtjmlhbayar.getText();
       String kemba=txtkembalian.getText();
       String jumlah = jmlhminum.getText();
       String nama = txtnamapembeli.getText();
       String subtotal = subtotalminum.getText();
        try{
        Statement state  = database.GetConnection().createStatement();
        state.executeUpdate("INSERT INTO transaksi2 (nofaktur, total, bayar, kembalian, nama) "
                + "VALUES('"+nofak+"','"+tot+"','"+by+"','"+kemba+"','"+nama+"')");
        JOptionPane.showMessageDialog(this, "Data Berhasil Di Tambah!");
        
     total();
        }catch (Exception e) {
             JOptionPane.showMessageDialog(this, "Data Tidak Berhasil Dibuat !");
        }
        
        }
    private void simpan(){
       String produk = (String) prodminum.getSelectedItem();
       String U = (String) ukuran.getSelectedItem();
       String harga = hargaminum.getText();
       String nofak=txtnofaktur.getText();
       String tot=asu.getText();
       String nama = txtnamapembeli.getText();
       String by=txtjmlhbayar.getText();
       String kemba=txtkembalian.getText();
       String jumlah = jmlhminum.getText();
       String subtotal = subtotalminum.getText();
       
        try{
        Statement state  = database.GetConnection().createStatement();
        state.executeUpdate("INSERT INTO keranjang (nofaktur,nama ,produk, ukuran, harga, jumlah_pesanan, totalharga, total, bayar, kembalian,tanggal) "
                + "VALUES('"+nofak+"', '"+nama+"','"+produk+"', '"+U+"','"+harga+"','"+jumlah+"', '"+subtotal+"','0','0','0',NOW())");
        JOptionPane.showMessageDialog(this, "Data Berhasil Di Tambah");
        
     total();
        }catch (Exception e) {
             JOptionPane.showMessageDialog(this, "Gak Bisa Bro!");
        }
        
        }
      private void simpandua(){

        try{
        Statement state  = database.GetConnection().createStatement();
        state.executeUpdate("");
        JOptionPane.showMessageDialog(this, "Data Berhasil Dibuat !");
        
     total();
        }catch (Exception e) {
             JOptionPane.showMessageDialog(this, "Data Tidak Berhasil Dibuat !");
        }
        
        }
    
    private void reset(){
        try {
            String sqla ="TRUNCATE keranjang";
            java.sql.Connection conn=(Connection)koneksi.getKoneksi();
            java.sql.PreparedStatement pst=conn.prepareStatement(sqla);
            pst.execute();
            tampilkan_data();
            JOptionPane.showMessageDialog(null, "Berhasil Di Reset, SILAHKAN ORDER LAGI", "proyek", JOptionPane.INFORMATION_MESSAGE);

    prodminum.setSelectedItem("===Pilih Minuman===");
    ukuran.setSelectedItem("=Pilih Ukuran Minuman=");
    hargaminum.setText("");
    jmlhminum.setText("");
    subtotalminum.setText("");
    txtjmlhbayar.setText("");
    txtkembalian.setText("");
    asu.setText("");
    txtnofaktur.setText("");
    txtnamapembeli.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
   
    
    private void total(){
        try {
            open_db();
            String cari = txtnofaktur.getText();
            String sql = "SELECT sum(totalharga) as asu FROM keranjang where nofaktur='"+cari+"'";
            st = (Statement) conn.createStatement();
            ResultSet r=st.executeQuery(sql);
            while(r.next()){
                asu.setText(r.getString("asu"));
            }
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null,"GAGAL");
        }
    }
    
    
    
    
    
    private void simpan1(){
       
       String ttl =asu.getText();
       String byr=txtjmlhbayar.getText();
       String kmbln = txtkembalian.getText();
        try{
        Statement state  = database.GetConnection().createStatement();
        state.executeUpdate("INSERT INTO transaksi (total,bayar,kembalian) "
                + "VALUES('"+ttl+"','"+byr+"', '"+kmbln+"')");
        JOptionPane.showMessageDialog(this, "Data Berhasil Dibuat !");
     total();
        }catch (Exception e) {
             JOptionPane.showMessageDialog(this, "Data Tidak Berhasil Dibuat !");
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

        jPanel1 = new javax.swing.JPanel();
        asu = new javax.swing.JTextField();
        asdf = new javax.swing.JLabel();
        simpan = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        refresh = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtkembalian = new javax.swing.JTextField();
        txtjmlhbayar = new javax.swing.JTextField();
        btnbayar = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        keranjang = new javax.swing.JTable();
        btntambahkanminum = new javax.swing.JButton();
        subtotalminum = new javax.swing.JTextField();
        jmlhminum = new javax.swing.JTextField();
        hargaminum = new javax.swing.JTextField();
        ukuran = new javax.swing.JComboBox<>();
        prodminum = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtnamapembeli = new javax.swing.JTextField();
        btnkembali = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtnofaktur = new javax.swing.JTextField();
        btnget = new javax.swing.JButton();
        asdf1 = new javax.swing.JLabel();
        asdf2 = new javax.swing.JLabel();
        asdf3 = new javax.swing.JLabel();
        asdf4 = new javax.swing.JLabel();
        asdf5 = new javax.swing.JLabel();
        asdf6 = new javax.swing.JLabel();
        asdf7 = new javax.swing.JLabel();
        asdf8 = new javax.swing.JLabel();
        asdf9 = new javax.swing.JLabel();
        asdf10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1920, 1080));
        setPreferredSize(new java.awt.Dimension(1920, 1080));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        asu.setEditable(false);
        asu.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        asu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                asuActionPerformed(evt);
            }
        });
        jPanel1.add(asu);
        asu.setBounds(1280, 810, 500, 35);

        asdf.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        asdf.setText(":");
        jPanel1.add(asdf);
        asdf.setBounds(310, 140, 20, 43);

        simpan.setBackground(new java.awt.Color(0, 0, 255));
        simpan.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        simpan.setForeground(new java.awt.Color(255, 255, 255));
        simpan.setText("SIMPAN");
        simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanActionPerformed(evt);
            }
        });
        jPanel1.add(simpan);
        simpan.setBounds(360, 810, 190, 39);

        delete.setBackground(new java.awt.Color(0, 0, 255));
        delete.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        delete.setForeground(new java.awt.Color(255, 255, 255));
        delete.setText("DELETE");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        jPanel1.add(delete);
        delete.setBounds(140, 810, 190, 39);

        refresh.setBackground(new java.awt.Color(0, 0, 255));
        refresh.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        refresh.setForeground(new java.awt.Color(255, 255, 255));
        refresh.setText("REFRESH");
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });
        jPanel1.add(refresh);
        refresh.setBounds(360, 890, 190, 39);

        jButton6.setBackground(new java.awt.Color(0, 0, 255));
        jButton6.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("CETAK");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6);
        jButton6.setBounds(140, 890, 190, 39);

        jLabel14.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        jLabel14.setText("KEMBALIAN");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(990, 970, 205, 30);

        txtkembalian.setEditable(false);
        txtkembalian.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jPanel1.add(txtkembalian);
        txtkembalian.setBounds(1280, 970, 499, 35);

        txtjmlhbayar.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtjmlhbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtjmlhbayarActionPerformed(evt);
            }
        });
        jPanel1.add(txtjmlhbayar);
        txtjmlhbayar.setBounds(1280, 860, 499, 35);

        btnbayar.setBackground(new java.awt.Color(0, 0, 255));
        btnbayar.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        btnbayar.setForeground(new java.awt.Color(255, 255, 255));
        btnbayar.setText("BAYAR");
        btnbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbayarActionPerformed(evt);
            }
        });
        jPanel1.add(btnbayar);
        btnbayar.setBounds(1280, 910, 499, 39);

        jLabel13.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        jLabel13.setText("JUMLAH BAYAR");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(990, 870, 205, 30);

        jLabel12.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        jLabel12.setText("TOTAL");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(990, 810, 205, 30);

        keranjang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Produk", "Ukuran", "Harga", "Jumlah", "Sub Total"
            }
        ));
        keranjang.setRowHeight(50);
        keranjang.setRowMargin(30);
        keranjang.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                keranjangAncestorRemoved(evt);
            }
        });
        keranjang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keranjangMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                keranjangMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(keranjang);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(140, 530, 1640, 262);

        btntambahkanminum.setBackground(new java.awt.Color(0, 0, 255));
        btntambahkanminum.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        btntambahkanminum.setForeground(new java.awt.Color(255, 255, 255));
        btntambahkanminum.setText("TAMBAHKAN");
        btntambahkanminum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntambahkanminumActionPerformed(evt);
            }
        });
        btntambahkanminum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btntambahkanminumKeyReleased(evt);
            }
        });
        jPanel1.add(btntambahkanminum);
        btntambahkanminum.setBounds(940, 440, 327, 78);

        subtotalminum.setEditable(false);
        subtotalminum.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jPanel1.add(subtotalminum);
        subtotalminum.setBounds(310, 470, 560, 43);

        jmlhminum.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jmlhminum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jmlhminumKeyReleased(evt);
            }
        });
        jPanel1.add(jmlhminum);
        jmlhminum.setBounds(310, 400, 560, 43);

        hargaminum.setEditable(false);
        hargaminum.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jPanel1.add(hargaminum);
        hargaminum.setBounds(310, 340, 560, 43);

        ukuran.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        ukuran.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "=Pilih Ukuran Minuman=", "250 ML", "500 ML", "1000 ML", "1500 ML", "=Pilih Ukuran Makanan=", "Big", "Standard", "Small" }));
        ukuran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ukuranActionPerformed(evt);
            }
        });
        jPanel1.add(ukuran);
        ukuran.setBounds(310, 280, 560, 43);

        prodminum.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        prodminum.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "===Pilih Minuman===", "Susu Original", "Susu Vanila", "Susu Coklat", "Susu Strawberry", "Susu Taro", "Susu Melon", "Susu Cappucino", "Susu Redvalved", "Susu Kopi Klasik", "Susu Greentea", "Susu Alpukad", "Susu Oreo", "Susu Cincau", "Susu Regal", "Susu Jahe", "Susu Ketan", "===Pilih Makanan===", "Puding Susu Sedot", "Naget Susu", "Sosis Bakar", "Sosis Goreng", "Burger", "Kentang Goreng" }));
        prodminum.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        prodminum.setInheritsPopupMenu(true);
        prodminum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prodminumActionPerformed(evt);
            }
        });
        jPanel1.add(prodminum);
        prodminum.setBounds(310, 200, 560, 43);

        jLabel6.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        jLabel6.setText("Sub Total");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(140, 480, 116, 30);

        jLabel5.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        jLabel5.setText("Jumlah");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(140, 410, 116, 30);

        jLabel3.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        jLabel3.setText("Ukuran");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(140, 290, 116, 30);

        jLabel4.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        jLabel4.setText("Harga");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(140, 350, 116, 30);

        jLabel2.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        jLabel2.setText("Produk");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(140, 210, 116, 30);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setText("TRANSAKSI");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(810, 20, 283, 58);

        jLabel7.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        jLabel7.setText("Nama Pembeli");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(940, 200, 169, 43);

        txtnamapembeli.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jPanel1.add(txtnamapembeli);
        txtnamapembeli.setBounds(1190, 200, 400, 44);

        btnkembali.setBackground(new java.awt.Color(0, 0, 255));
        btnkembali.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        btnkembali.setForeground(new java.awt.Color(255, 255, 255));
        btnkembali.setText("KEMBALI");
        btnkembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkembaliActionPerformed(evt);
            }
        });
        jPanel1.add(btnkembali);
        btnkembali.setBounds(15, 16, 200, 50);

        jLabel8.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        jLabel8.setText("NO FAKTUR");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(140, 150, 147, 30);

        txtnofaktur.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jPanel1.add(txtnofaktur);
        txtnofaktur.setBounds(340, 150, 400, 35);

        btnget.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        btnget.setText("GET");
        btnget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngetActionPerformed(evt);
            }
        });
        jPanel1.add(btnget);
        btnget.setBounds(750, 150, 120, 40);

        asdf1.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        asdf1.setText("No Faktur");
        jPanel1.add(asdf1);
        asdf1.setBounds(1190, 140, 210, 43);

        asdf2.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        asdf2.setText(":");
        jPanel1.add(asdf2);
        asdf2.setBounds(280, 200, 20, 43);

        asdf3.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        asdf3.setText(":");
        jPanel1.add(asdf3);
        asdf3.setBounds(280, 280, 20, 43);

        asdf4.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        asdf4.setText(":");
        jPanel1.add(asdf4);
        asdf4.setBounds(280, 340, 20, 43);

        asdf5.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        asdf5.setText(":");
        jPanel1.add(asdf5);
        asdf5.setBounds(280, 400, 20, 43);

        asdf6.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        asdf6.setText(":");
        jPanel1.add(asdf6);
        asdf6.setBounds(280, 470, 20, 43);

        asdf7.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        asdf7.setText(":");
        jPanel1.add(asdf7);
        asdf7.setBounds(1240, 970, 20, 30);

        asdf8.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        asdf8.setText(":");
        jPanel1.add(asdf8);
        asdf8.setBounds(1160, 200, 20, 43);

        asdf9.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        asdf9.setText(":");
        jPanel1.add(asdf9);
        asdf9.setBounds(1240, 810, 20, 30);

        asdf10.setFont(new java.awt.Font("Square721 BT", 1, 24)); // NOI18N
        asdf10.setText(":");
        jPanel1.add(asdf10);
        asdf10.setBounds(1240, 860, 20, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void prodminumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prodminumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prodminumActionPerformed

    private void ukuranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ukuranActionPerformed
        setHargaMinuman();
    }//GEN-LAST:event_ukuranActionPerformed

    private void btntambahkanminumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahkanminumActionPerformed
        
        
        simpan();
        tampilkan_data();
    }//GEN-LAST:event_btntambahkanminumActionPerformed

    private void btnbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbayarActionPerformed
        int totalBayar = Integer.parseInt(asu.getText());
        int jumlahBayar = Integer.parseInt(txtjmlhbayar.getText());
        
        txtkembalian.setText("" + (jumlahBayar-totalBayar));
    }//GEN-LAST:event_btnbayarActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        reset();
    }//GEN-LAST:event_refreshActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
    
            Tabelklik();
    
    }//GEN-LAST:event_deleteActionPerformed

    private void keranjangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keranjangMousePressed
        
    }//GEN-LAST:event_keranjangMousePressed

    private void keranjangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keranjangMouseClicked
 
            
          
         // TODO add your handling code here:
    }//GEN-LAST:event_keranjangMouseClicked

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
    simpan2();
    
    Simpan();
    }//GEN-LAST:event_simpanActionPerformed

    private void keranjangAncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_keranjangAncestorRemoved
        
    }//GEN-LAST:event_keranjangAncestorRemoved

    private void btntambahkanminumKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btntambahkanminumKeyReleased
simpandua();  
// TODO add your handling code here:
    }//GEN-LAST:event_btntambahkanminumKeyReleased

    private void jmlhminumKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jmlhminumKeyReleased
        int harga = Integer.parseInt(hargaminum.getText());
        int jumlah = Integer.parseInt(jmlhminum.getText());
        int total = (harga * jumlah);
        subtotalminum.setText(Integer.toString(total));  
    }//GEN-LAST:event_jmlhminumKeyReleased

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try{
            String file = "struktransaksi.jasper";
            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            HashMap param = new HashMap();
            
            param.put("produk",prodminum.getSelectedItem());
            param.put("jumlah",jmlhminum.getText());
            param.put("harga",subtotalminum.getText());
            param.put("nofaktur",txtnofaktur.getText());
            param.put("total",asu.getText());
            param.put("bayar",txtjmlhbayar.getText());
            param.put("kembalian",txtkembalian.getText());
            
            
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream(file),param,koneksi.getKoneksi());
            JasperViewer.viewReport(print, false);
            
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException | JRException e){
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txtjmlhbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtjmlhbayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtjmlhbayarActionPerformed

    private void asuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_asuActionPerformed

    private void btngetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngetActionPerformed
        nofaktur();
    }//GEN-LAST:event_btngetActionPerformed

    private void btnkembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkembaliActionPerformed
        new menupetugas().setVisible(true);
    }//GEN-LAST:event_btnkembaliActionPerformed

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
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel asdf;
    private javax.swing.JLabel asdf1;
    private javax.swing.JLabel asdf10;
    private javax.swing.JLabel asdf2;
    private javax.swing.JLabel asdf3;
    private javax.swing.JLabel asdf4;
    private javax.swing.JLabel asdf5;
    private javax.swing.JLabel asdf6;
    private javax.swing.JLabel asdf7;
    private javax.swing.JLabel asdf8;
    private javax.swing.JLabel asdf9;
    private javax.swing.JTextField asu;
    private javax.swing.JButton btnbayar;
    private javax.swing.JButton btnget;
    private javax.swing.JButton btnkembali;
    private javax.swing.JButton btntambahkanminum;
    private javax.swing.JButton delete;
    private javax.swing.JTextField hargaminum;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jmlhminum;
    private javax.swing.JTable keranjang;
    private javax.swing.JComboBox<String> prodminum;
    private javax.swing.JButton refresh;
    private javax.swing.JButton simpan;
    private javax.swing.JTextField subtotalminum;
    private javax.swing.JTextField txtjmlhbayar;
    private javax.swing.JTextField txtkembalian;
    private javax.swing.JTextField txtnamapembeli;
    private javax.swing.JTextField txtnofaktur;
    private javax.swing.JComboBox<String> ukuran;
    // End of variables declaration//GEN-END:variables

 private void open_db() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1/proyek", "root", "");
            st = (Statement) conn.createStatement();

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Koneksi gagal");
            System.out.println(e.getMessage());
        }
    }
 private void tampilkan_data() {
     
        DefaultTableModel tabelmapel = new DefaultTableModel();
        tabelmapel.addColumn("No");
        tabelmapel.addColumn("Nofaktur");
        tabelmapel.addColumn("Nama");
        tabelmapel.addColumn("Produk");
        tabelmapel.addColumn("Ukuran");
        tabelmapel.addColumn("Harga");
        tabelmapel.addColumn("Jumlah");
        tabelmapel.addColumn("Total");
        
        try {
            open_db();
            String cari = txtnofaktur.getText();
             String sql = "select * from keranjang where nofaktur='"+cari+"'";
            st = (Statement) conn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while (rs.next()) {
                Object[] o =new Object[8];
                o[0] = rs.getString("id");
                o[1] = rs.getString("nofaktur");
                o[2] = rs.getString("nama");
                o[3] = rs.getString("produk");
                o[4] = rs.getString("ukuran");
                o[5] = rs.getString("harga");
                o[6] = rs.getString("jumlah_pesanan");
                o[7] = rs.getString("totalharga");
                tabelmapel.addRow(o);
                
            }
           keranjang.setModel(tabelmapel);
        } catch (Exception e) {
        }
    }
 private void nofaktur() {
        try {
            open_db();
            String sql = "SELECT * FROM transaksi ORDER by nofaktur desc";
            st = (Statement) conn.createStatement();
            ResultSet r=st.executeQuery(sql);

            if (r.next()) {
                
                
                String nofak = r.getString("nofaktur").substring(1);
                String AN = "" + (Integer.parseInt(nofak) + 1);
                String Nol = "";

                if (AN.length() == 1) {
                    Nol = "00000000";
                } else if (AN.length() == 2) {
                    Nol = "0000000";
                } else if (AN.length() == 3) {
                    Nol = "000000";
                } else if (AN.length() == 4) {
                    Nol = "00000";
                 } else if (AN.length() == 5) {
                    Nol = "0000";
                 }
                else if (AN.length() == 6) {
                    Nol = "000";
                 }
                else if (AN.length() == 7) {
                    Nol = "00";
                 }
                else if (AN.length() == 8) {
                    Nol = "0";
                 }
                 else if (AN.length() == 9) {
                    Nol = "";
                 }


                txtnofaktur.setText("F" + Nol + AN);
            } else {
                txtnofaktur.setText("F000000001");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
 
    private void Tabelklik(){
        int pilih = keranjang.getSelectedRow();
        String id = (String) keranjang.getValueAt(pilih, 0);
         try {
            Statement state  = database.GetConnection().createStatement();
            state.executeUpdate("delete from keranjang where id='"+id+"';");
            JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus !");
            tampilkan_data();
            total();
            state.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data Gagal Dihapus !");
        }
    }
   
    
}
