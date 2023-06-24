
import java.awt.Color;
import Database.DatabaseConnection;
import java.awt.Component;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author acer
 */
public class SurveyScreen extends javax.swing.JFrame {

    /**
     * Creates new form SurveyScreen
     */
    public SurveyScreen() {
        initComponents();
         setExtendedState(JFrame.MAXIMIZED_BOTH);
       
        for(Component c:dateID.getComponents()){
            ((JComponent)c).setBackground(new Color(51,51,255));
        }
    }
    
    //Method to return all selected/Checked items from favourite food    
    public String getCheckedBox(JCheckBox pizza,JCheckBox pasta,JCheckBox pap,JCheckBox chick,
                                JCheckBox beef,JCheckBox other){
        
       String  
               myPizza="",
               myPasta="",
               myPap="",
               myChick="",
               myBeef="",
               myOther="";
      
    
        if(pizza.isSelected()){
        
           myPizza=" (Pizza) ";
        }if(pasta.isSelected()){
        
           myPasta=" (Pasta) ";
        }if(pap.isSelected()){
        
           myPap=" (Pap and wors) ";
        }if(chick.isSelected()){
        
           myChick=" (Chicken stir fry) ";
        }if(beef.isSelected()){
        
           myBeef=" (Beef stir fry) ";
        }if(other.isSelected()){
        
           myOther=" (Other) ";
        }else{
            myOther=" (None) ";
        }
        return myPizza+""+myPasta+""+myPap+""+myChick+""+myBeef+""+myOther;
    }
    
    //Return the rating value ffor rating
    public String getEatOutSelected(){
        
        String value;
        if(SA1.isSelected()){
            value="1";
        }else if(A1.isSelected()){
            value="2";
        }else if(N1.isSelected()){
            value="3";
        }else if(D1.isSelected()){
            value="4";
        }else if(SD1.isSelected()){
            value="5";
        }else{
            
            value="6";
        }
        eatLabel.setForeground(new Color(255,255,255));
            return value;
            
    }
    
    public String getWatchMovieSelected(){
        
        String value;
        if(SA2.isSelected()){
            value="1";
        }else if(A2.isSelected()){
            value="2";
        }else if(N2.isSelected()){
            value="3";
        }else if(D2.isSelected()){
            value="4";
        }else if(SD2.isSelected()){
            value="5";
        }else{
            value="6";
        }
         movieLabel.setForeground(new Color(255,255,255));
            return value;
    }
    
    public String getWatchTVSelected(){
        
        String value;
        if(SA3.isSelected()){
            value="1";
        }else if(A3.isSelected()){
            value="2";
        }else if(N3.isSelected()){
            value="3";
        }else if(D3.isSelected()){
            value="4";
        }else if(SD3.isSelected()){
            value="5";
        }else{
            value="6";
        }
         tvLabel.setForeground(new Color(255,255,255));
            return value;
    }
     
    public String getRadioSelected(){
        
        String value;
        if(SA4.isSelected()){
            value="1";
        }else if(A4.isSelected()){
            value="2";
        }else if(N4.isSelected()){
            value="3";
        }else if(D4.isSelected()){
            value="4";
        }else if(SD4.isSelected()){
            value="5";
        }else{
            value="6";
        }
         radioLabel.setForeground(new Color(255,255,255));
            return value;
    }
    
    //Populate hobby SQL table  
    public void getHobbyData(){
      
          Connection conn = DatabaseConnection.getConn();
          String contact = contactID.getText();
          String eatValue = getEatOutSelected();
          String movieVlue = getWatchMovieSelected();
          String tvValue = getWatchTVSelected();
          String radioValue = getRadioSelected();
         
          try{
              
               Statement st = conn.createStatement();
               String sqlQuery = "INSERT INTO hobby VALUES((SELECT contact_number FROM user_info WHERE contact_number ='"+contact+"'),'"+eatValue+"','"+movieVlue+"','"+tvValue+"','"+radioValue+"')";
               st.executeUpdate(sqlQuery);
          
          }
          catch(Exception e){
              
              JOptionPane.showMessageDialog(this, "hobby error"+e.getMessage());
          }
      }
    
    //populate user_info SQL table
    public void getUserData(){
        
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        String surname = surnameID.getText();
        String firstName = nameID.getText();
        String contact = contactID.getText();
        String date = dFormat.format(dateID.getDate());
        String age = ageID.getText();
        String favFoodList = getCheckedBox(pizzaBox,pasta,pap,chick,beef,other);
       
    
        try{
            
     
            
            Connection conn = DatabaseConnection.getConn();
            String sqlQuery = "INSERT INTO user_info VALUES('"+surname+"','"+firstName+"','"+contact+"','"+date+"','"+age+"','"+favFoodList+"')";
            Statement st = conn.createStatement();
            st.executeUpdate(sqlQuery);
            getHobbyData();
            
            JOptionPane.showMessageDialog(this, "Data submitted successfully ".toUpperCase());
            setVisible(false);
            new HomeScreen().setVisible(true);
       
        
       
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, "Something went wrong"+e.getMessage());
            
        }
    }
    
    public void errorTextFieldColor(JTextField textField){
    
        textField.setForeground(new Color(255,255,51));
        textField.setBackground(new Color(240,0,0,100));
        textField.setText(" error: field required ");
    }
    
    //Clears textField with error message to default state
    public void clearErrorTextField(JTextField textField){
        
     textField.setForeground(new Color(0,0,0));
     textField.setBackground(new Color(51,51,255));
     if(textField.getText().equals(" error: field required ")){
         textField.setText("");
     }
        
    }
    
    //check if date is not null or in proper format
    public boolean dateValidation(){
        
        try{
            SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dFormat.format(dateID.getDate());
        }catch(Exception e){
            
            JOptionPane.showMessageDialog(this, "Invalid Date");
            return false;
        }
        return true;
    }
    
    public boolean isInteger(String contact){
        
        try{
            
            Integer.parseInt(contact);
            return true;
        }catch(NumberFormatException e){
            return false;
            
        }
    }
    
   public boolean emptyTextFieldValidation(){
       
       try{
           
            if(surnameID.getText().equals("")){
            
            errorTextFieldColor(surnameID);
            return false;
            
        }else if(nameID.getText().equals("")){
            
            errorTextFieldColor(nameID);
            return false;
        }else if(contactID.getText().equals("") ){
            
            errorTextFieldColor(contactID);
            return false;
        }else if(contactID.getText().length()>15 || contactID.getText().length()<10){
            
            JOptionPane.showMessageDialog(this, "10 to 15 digits allowed for Contact Number");
            return false;
        }else if(isInteger(contactID.getText())==true){
            JOptionPane.showMessageDialog(null, "Digits are expected for contact Field");
            return false;
        }else if(ageID.getText().equals("")){
            
            errorTextFieldColor(ageID);
            return false;
        }else if(Integer.parseInt(ageID.getText())<5 || Integer.parseInt(ageID.getText())>120){
            
            JOptionPane.showMessageDialog(this, "You must be at least 5 years OR not more than 120 year");
            return false;
            
        }
       else{
            
            return true;
        }
       }catch(Exception e){
           
           JOptionPane.showMessageDialog(this, e.getMessage());
       }
        return true;
       
   }
   
   //check if radiaButons are selected
   public boolean RadioButtonValidation(){
       
           if(getEatOutSelected().equals("6")){
            
            eatLabel.setForeground(new Color(255,51,51));
            JOptionPane.showMessageDialog(this, "Please select option");
            return false;
        }else if(getWatchMovieSelected().equals("6")){
            
            movieLabel.setForeground(new Color(255,51,51));
            JOptionPane.showMessageDialog(this, "Please select option");
            return false;
        }else if(getWatchTVSelected().equals("6")){
            
            tvLabel.setForeground(new Color(255,51,51));
            JOptionPane.showMessageDialog(this, "Please select option");
            return false;
        }else if(getRadioSelected().equals("6")){
            
            radioLabel.setForeground(new Color(255,51,51));
            JOptionPane.showMessageDialog(this, "Please select option");
            return false;
        }else{
            
            return true;
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

        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        DATETEXT = new javax.swing.JLabel();
        ageIDTEXT = new javax.swing.JLabel();
        surnameID = new javax.swing.JTextField();
        nameID = new javax.swing.JTextField();
        contactID = new javax.swing.JTextField();
        ageID = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        dateID = new com.toedter.calendar.JDateChooser();
        radioLabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        tvLabel = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        movieLabel = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        eatLabel = new javax.swing.JLabel();
        SA4 = new javax.swing.JRadioButton();
        SA3 = new javax.swing.JRadioButton();
        SA2 = new javax.swing.JRadioButton();
        SA1 = new javax.swing.JRadioButton();
        A1 = new javax.swing.JRadioButton();
        A2 = new javax.swing.JRadioButton();
        A3 = new javax.swing.JRadioButton();
        A4 = new javax.swing.JRadioButton();
        N4 = new javax.swing.JRadioButton();
        N3 = new javax.swing.JRadioButton();
        N2 = new javax.swing.JRadioButton();
        N1 = new javax.swing.JRadioButton();
        D1 = new javax.swing.JRadioButton();
        D2 = new javax.swing.JRadioButton();
        D3 = new javax.swing.JRadioButton();
        D4 = new javax.swing.JRadioButton();
        SD4 = new javax.swing.JRadioButton();
        SD3 = new javax.swing.JRadioButton();
        SD2 = new javax.swing.JRadioButton();
        SD1 = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        other = new javax.swing.JCheckBox();
        beef = new javax.swing.JCheckBox();
        chick = new javax.swing.JCheckBox();
        pap = new javax.swing.JCheckBox();
        pasta = new javax.swing.JCheckBox();
        pizzaBox = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 255));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Microsoft PhagsPa", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("On the scale of 1 to 5 indicate whether you strongly agree to strongly disagree");
        jLabel8.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(656, 340, 550, 20));

        jPanel1.setBackground(new java.awt.Color(51, 51, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 768));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Surname");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 153, 269, 35));

        jLabel2.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("First Name");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 223, 269, 35));

        jLabel3.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Contact Number");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 295, 269, 35));

        DATETEXT.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        DATETEXT.setForeground(new java.awt.Color(255, 255, 255));
        DATETEXT.setText("Date");
        jPanel1.add(DATETEXT, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 366, 269, 35));

        ageIDTEXT.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        ageIDTEXT.setForeground(new java.awt.Color(255, 255, 255));
        ageIDTEXT.setText("Age");
        jPanel1.add(ageIDTEXT, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 438, 269, 35));

        surnameID.setBackground(new java.awt.Color(51, 51, 255));
        surnameID.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        surnameID.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 0, 153)));
        surnameID.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                surnameIDMouseClicked(evt);
            }
        });
        jPanel1.add(surnameID, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 190, 269, 30));

        nameID.setBackground(new java.awt.Color(51, 51, 255));
        nameID.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        nameID.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 0, 153)));
        nameID.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nameIDMouseClicked(evt);
            }
        });
        jPanel1.add(nameID, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 262, 269, 30));

        contactID.setBackground(new java.awt.Color(51, 51, 255));
        contactID.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        contactID.setToolTipText("");
        contactID.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 0, 153)));
        contactID.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                contactIDMouseClicked(evt);
            }
        });
        jPanel1.add(contactID, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 333, 269, 30));

        ageID.setBackground(new java.awt.Color(51, 51, 255));
        ageID.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        ageID.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 0, 153)));
        ageID.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ageIDMouseClicked(evt);
            }
        });
        jPanel1.add(ageID, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 476, 269, 30));

        jLabel6.setFont(new java.awt.Font("Arial Black", 3, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 51));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Presona Details  ");
        jLabel6.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 0)));
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 66, 269, -1));
        jPanel1.add(dateID, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 405, 270, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 400, 630));

        radioLabel.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        radioLabel.setForeground(new java.awt.Color(255, 255, 255));
        radioLabel.setText("I like to listen to radio");
        getContentPane().add(radioLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(486, 564, 165, 25));

        jSeparator2.setBackground(new java.awt.Color(51, 51, 255));
        jSeparator2.setForeground(new java.awt.Color(51, 51, 255));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 540, 855, 10));

        tvLabel.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        tvLabel.setForeground(new java.awt.Color(255, 255, 255));
        tvLabel.setText("I like to watch TV");
        getContentPane().add(tvLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(486, 517, 160, 25));

        jSeparator9.setBackground(new java.awt.Color(51, 51, 255));
        jSeparator9.setForeground(new java.awt.Color(51, 51, 255));
        getContentPane().add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 500, 855, 10));

        movieLabel.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        movieLabel.setForeground(new java.awt.Color(255, 255, 255));
        movieLabel.setText("like to watch movie");
        getContentPane().add(movieLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(486, 470, 160, 25));

        jSeparator8.setBackground(new java.awt.Color(51, 51, 255));
        jSeparator8.setForeground(new java.awt.Color(51, 51, 255));
        getContentPane().add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 460, 855, 10));

        jSeparator7.setBackground(new java.awt.Color(51, 51, 255));
        jSeparator7.setForeground(new java.awt.Color(51, 51, 255));
        getContentPane().add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 420, 855, 5));

        eatLabel.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        eatLabel.setForeground(new java.awt.Color(255, 255, 255));
        eatLabel.setText("Ilike to eat out");
        getContentPane().add(eatLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(486, 423, 160, 25));

        SA4.setOpaque(false);
        getContentPane().add(SA4, new org.netbeans.lib.awtextra.AbsoluteConstraints(723, 552, -1, -1));

        SA3.setOpaque(false);
        getContentPane().add(SA3, new org.netbeans.lib.awtextra.AbsoluteConstraints(723, 509, -1, -1));

        SA2.setOpaque(false);
        getContentPane().add(SA2, new org.netbeans.lib.awtextra.AbsoluteConstraints(723, 466, -1, -1));

        SA1.setBackground(new java.awt.Color(51, 255, 51));
        SA1.setForeground(new java.awt.Color(255, 51, 51));
        SA1.setOpaque(false);
        getContentPane().add(SA1, new org.netbeans.lib.awtextra.AbsoluteConstraints(723, 423, -1, -1));

        A1.setOpaque(false);
        getContentPane().add(A1, new org.netbeans.lib.awtextra.AbsoluteConstraints(853, 423, -1, -1));

        A2.setOpaque(false);
        getContentPane().add(A2, new org.netbeans.lib.awtextra.AbsoluteConstraints(853, 466, -1, -1));

        A3.setOpaque(false);
        getContentPane().add(A3, new org.netbeans.lib.awtextra.AbsoluteConstraints(853, 509, -1, -1));

        A4.setOpaque(false);
        getContentPane().add(A4, new org.netbeans.lib.awtextra.AbsoluteConstraints(853, 552, -1, -1));

        N4.setOpaque(false);
        getContentPane().add(N4, new org.netbeans.lib.awtextra.AbsoluteConstraints(969, 552, -1, -1));

        N3.setOpaque(false);
        N3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                N3ActionPerformed(evt);
            }
        });
        getContentPane().add(N3, new org.netbeans.lib.awtextra.AbsoluteConstraints(969, 509, -1, -1));

        N2.setOpaque(false);
        getContentPane().add(N2, new org.netbeans.lib.awtextra.AbsoluteConstraints(969, 466, -1, -1));

        N1.setOpaque(false);
        getContentPane().add(N1, new org.netbeans.lib.awtextra.AbsoluteConstraints(969, 423, -1, -1));

        D1.setOpaque(false);
        getContentPane().add(D1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1098, 423, -1, -1));

        D2.setOpaque(false);
        getContentPane().add(D2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1098, 466, -1, -1));

        D3.setOpaque(false);
        D3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D3ActionPerformed(evt);
            }
        });
        getContentPane().add(D3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1098, 509, -1, -1));

        D4.setOpaque(false);
        getContentPane().add(D4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1098, 552, -1, -1));

        SD4.setOpaque(false);
        getContentPane().add(SD4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 552, -1, -1));

        SD3.setOpaque(false);
        SD3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SD3ActionPerformed(evt);
            }
        });
        getContentPane().add(SD3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 509, -1, -1));

        SD2.setOpaque(false);
        getContentPane().add(SD2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 466, -1, -1));

        SD1.setOpaque(false);
        getContentPane().add(SD1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 423, -1, -1));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(129, 234, 232));
        jLabel11.setText("Strongly Agree");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 390, 107, -1));

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(129, 234, 232));
        jLabel12.setText("Agree");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 390, -1, -1));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(129, 234, 232));
        jLabel13.setText("Neutral");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 390, -1, -1));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(129, 234, 232));
        jLabel14.setText("Disagree");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 390, -1, -1));

        jLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(129, 234, 232));
        jLabel15.setText("Strongly disagree");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 390, -1, -1));

        other.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        other.setForeground(new java.awt.Color(255, 255, 255));
        other.setText("Other  ");
        other.setOpaque(false);
        getContentPane().add(other, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 320, 80, -1));

        beef.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        beef.setForeground(new java.awt.Color(255, 255, 255));
        beef.setText("Beef stir fry  ");
        beef.setOpaque(false);
        getContentPane().add(beef, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 290, 110, -1));

        chick.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        chick.setForeground(new java.awt.Color(255, 255, 255));
        chick.setText("Chiken stir fry  ");
        chick.setOpaque(false);
        getContentPane().add(chick, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 260, 140, -1));

        pap.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        pap.setForeground(new java.awt.Color(255, 255, 255));
        pap.setText("Pap and wors  ");
        pap.setOpaque(false);
        getContentPane().add(pap, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 230, 140, -1));

        pasta.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        pasta.setForeground(new java.awt.Color(255, 255, 255));
        pasta.setText("Pasta  ");
        pasta.setOpaque(false);
        getContentPane().add(pasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 200, -1, -1));

        pizzaBox.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        pizzaBox.setForeground(new java.awt.Color(255, 255, 255));
        pizzaBox.setText("Pizza  ");
        pizzaBox.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        pizzaBox.setOpaque(false);
        getContentPane().add(pizzaBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 170, -1, -1));

        jLabel4.setFont(new java.awt.Font("Microsoft PhagsPa", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("What is your favourite food ? (You can choose more than 1 answer) ");
        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 120, -1, -1));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 90, 830, -1));

        jLabel7.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 51));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Take our survey");
        jLabel7.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 0)));
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, 300, -1));

        jButton1.setBackground(new java.awt.Color(51, 51, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(129, 234, 232));
        jButton1.setText(" Submit  ");
        jButton1.setBorderPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 620, 209, 40));

        jLabel9.setFont(new java.awt.Font("Segoe Script", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 153, 0));
        jLabel9.setText("Programming test intern");
        jLabel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 255), 1, true));
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 660, -1, -1));

        jLabel5.setForeground(new java.awt.Color(0, 51, 51));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/backgroundImages/Ads_Q3-2021-Forrester-Wave_MX-Blog-Header-1500-x-700-1(0).jpg"))); // NOI18N
        jLabel5.setText("jLabel5");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void surnameIDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_surnameIDMouseClicked
        // TODO add your handling code here:
        clearErrorTextField(surnameID);
    }//GEN-LAST:event_surnameIDMouseClicked

    private void nameIDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nameIDMouseClicked
        // TODO add your handling code here:
        clearErrorTextField(nameID);
    }//GEN-LAST:event_nameIDMouseClicked

    private void contactIDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contactIDMouseClicked
        // TODO add your handling code here:
        clearErrorTextField(contactID);
    }//GEN-LAST:event_contactIDMouseClicked

    private void ageIDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ageIDMouseClicked
        // TODO add your handling code here:
        clearErrorTextField(ageID);
    }//GEN-LAST:event_ageIDMouseClicked

    private void N3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_N3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_N3ActionPerformed

    private void D3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_D3ActionPerformed

    private void SD3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SD3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SD3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
     
///https://github.com/sanraf/SurveyProject.git
        if(emptyTextFieldValidation()==true && dateValidation()==true){
            if( RadioButtonValidation()==true){
                getUserData();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(SurveyScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SurveyScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SurveyScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SurveyScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SurveyScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton A1;
    private javax.swing.JRadioButton A2;
    private javax.swing.JRadioButton A3;
    private javax.swing.JRadioButton A4;
    private javax.swing.JRadioButton D1;
    private javax.swing.JRadioButton D2;
    private javax.swing.JRadioButton D3;
    private javax.swing.JRadioButton D4;
    private javax.swing.JLabel DATETEXT;
    private javax.swing.JRadioButton N1;
    private javax.swing.JRadioButton N2;
    private javax.swing.JRadioButton N3;
    private javax.swing.JRadioButton N4;
    private javax.swing.JRadioButton SA1;
    private javax.swing.JRadioButton SA2;
    private javax.swing.JRadioButton SA3;
    private javax.swing.JRadioButton SA4;
    private javax.swing.JRadioButton SD1;
    private javax.swing.JRadioButton SD2;
    private javax.swing.JRadioButton SD3;
    private javax.swing.JRadioButton SD4;
    private javax.swing.JTextField ageID;
    private javax.swing.JLabel ageIDTEXT;
    private javax.swing.JCheckBox beef;
    private javax.swing.JCheckBox chick;
    private javax.swing.JTextField contactID;
    private com.toedter.calendar.JDateChooser dateID;
    private javax.swing.JLabel eatLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel movieLabel;
    private javax.swing.JTextField nameID;
    private javax.swing.JCheckBox other;
    private javax.swing.JCheckBox pap;
    private javax.swing.JCheckBox pasta;
    private javax.swing.JCheckBox pizzaBox;
    private javax.swing.JLabel radioLabel;
    private javax.swing.JTextField surnameID;
    private javax.swing.JLabel tvLabel;
    // End of variables declaration//GEN-END:variables
}
