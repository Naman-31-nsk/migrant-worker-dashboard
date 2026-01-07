import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.io.File;

public class MigrantWorkerDashboard extends JFrame {
    
    private List<MigrantWorker> workers = new ArrayList<>();
    private List<Landlord> landlords = new ArrayList<>();
    private List<VerificationRecord> verifications = new ArrayList<>();
    private List<LandlordWorkerLink> landlordWorkerLinks = new ArrayList<>();
    
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    private static final String WORKER_PASSWORD = "300";
    private static final String LANDLORD_PASSWORD = "3000";
    
    public MigrantWorkerDashboard() {
        setTitle("Enhanced Migrant Worker Management System");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        addSampleData();
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        mainPanel.add(createWelcomeScreen(), "WELCOME");
        
        add(mainPanel);
        cardLayout.show(mainPanel, "WELCOME");
        
        setVisible(true);
    }
    
    private JPanel createWelcomeScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 247, 250));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(25, 118, 210));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel titleLabel = new JLabel("Migrant Worker Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        panel.add(headerPanel, BorderLayout.NORTH);
        
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(245, 247, 250));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JPanel welcomeBox = new JPanel();
        welcomeBox.setLayout(new BoxLayout(welcomeBox, BoxLayout.Y_AXIS));
        welcomeBox.setBackground(Color.WHITE);
        welcomeBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        
        JLabel welcomeTitle = new JLabel("Welcome to the Dashboard");
        welcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeTitle.setForeground(new Color(33, 33, 33));
        welcomeTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextArea introText = new JTextArea(
            "Enhanced Features:\n\n" +
            "✓ Complete Migrant Worker Profile with Documents\n" +
            "✓ Family Details & Guardian Information\n" +
            "✓ Landlord-Worker Registration Linking\n" +
            "✓ Real-time Credential Verification\n" +
            "✓ Police Alert System for Security\n\n" +
            "Choose an option below to get started:"
        );
        introText.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        introText.setForeground(new Color(66, 66, 66));
        introText.setLineWrap(true);
        introText.setWrapStyleWord(true);
        introText.setEditable(false);
        introText.setOpaque(false);
        introText.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        introText.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        welcomeBox.add(welcomeTitle);
        welcomeBox.add(introText);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(welcomeBox, gbc);
        
        JButton addWorkerBtn = createWelcomeButton("Add Migrant Worker", new Color(76, 175, 80), 300, 60);
        JButton addLandlordBtn = createWelcomeButton("Add Landlord", new Color(156, 39, 176), 300, 60);
        JButton linkBtn = createWelcomeButton("Link Worker to Landlord", new Color(255, 87, 34), 300, 60);
        JButton viewWorkersBtn = createWelcomeButton("View Migrant Workers", new Color(33, 150, 243), 300, 60);
        JButton viewLandlordsBtn = createWelcomeButton("View Landlords", new Color(255, 152, 0), 300, 60);
        JButton viewLinksBtn = createWelcomeButton("Landlord-Worker Dashboard", new Color(0, 150, 136), 300, 60);
        JButton verifyBtn = createWelcomeButton("Verify Credentials", new Color(244, 67, 54), 300, 60);
        JButton dashboardBtn = createWelcomeButton("Full Dashboard", new Color(103, 58, 183), 300, 60);
        
        addWorkerBtn.addActionListener(e -> showAddWorkerDialog());
        addLandlordBtn.addActionListener(e -> showAddLandlordDialog());
        linkBtn.addActionListener(e -> showLinkDialog());
        viewWorkersBtn.addActionListener(e -> authenticateAndShow("workers"));
        viewLandlordsBtn.addActionListener(e -> authenticateAndShow("landlords"));
        viewLinksBtn.addActionListener(e -> showLandlordWorkerDashboard());
        verifyBtn.addActionListener(e -> showVerificationDialog());
        dashboardBtn.addActionListener(e -> showFullDashboard());
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        contentPanel.add(addWorkerBtn, gbc);
        
        gbc.gridx = 1;
        contentPanel.add(addLandlordBtn, gbc);
        
        gbc.gridy = 2;
        gbc.gridx = 0;
        contentPanel.add(linkBtn, gbc);
        
        gbc.gridx = 1;
        contentPanel.add(viewWorkersBtn, gbc);
        
        gbc.gridy = 3;
        gbc.gridx = 0;
        contentPanel.add(viewLandlordsBtn, gbc);
        
        gbc.gridx = 1;
        contentPanel.add(viewLinksBtn, gbc);
        
        gbc.gridy = 4;
        gbc.gridx = 0;
        contentPanel.add(verifyBtn, gbc);
        
        gbc.gridx = 1;
        contentPanel.add(dashboardBtn, gbc);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(245, 247, 250));
        JLabel footerLabel = new JLabel("2024 Migrant Worker Management System");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel.setForeground(new Color(120, 120, 120));
        footerPanel.add(footerLabel);
        panel.add(footerPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JButton createWelcomeButton(String text, Color bgColor, int width, int height) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(width, height));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });
        
        return btn;
    }
    
    private void showAddWorkerDialog() {
        JDialog dialog = new JDialog(this, "Add New Migrant Worker", true);
        dialog.setSize(900, 750);
        dialog.setLocationRelativeTo(this);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Personal Details Tab
        JPanel personalPanel = new JPanel(new GridBagLayout());
        personalPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField nameField = new JTextField(20);
        JTextField mobileField = new JTextField(20);
        JTextField dobField = new JTextField(20);
        JTextField ageField = new JTextField(20);
        JComboBox<String> genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        JTextField domicileField = new JTextField(20);
        JTextField stateFromField = new JTextField(20);
        JTextField districtField = new JTextField(20);
        JTextField destinationStateField = new JTextField(20);
        JTextField jobTitleField = new JTextField(20);
        JTextField skillSetField = new JTextField(20);
        
        JLabel photoLabel = new JLabel("No photo selected");
        JButton uploadPhotoBtn = new JButton("Upload Photo");
        JLabel docLabel = new JLabel("No document selected");
        JButton uploadDocBtn = new JButton("Upload Niwas Praman Patra");
        
        final String[] photoPath = {""};
        final String[] docPath = {""};
        
        uploadPhotoBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "jpeg"));
            if (fileChooser.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                photoPath[0] = file.getAbsolutePath();
                photoLabel.setText(file.getName());
            }
        });
        
        uploadDocBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Documents", "pdf", "jpg", "png"));
            if (fileChooser.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                docPath[0] = file.getAbsolutePath();
                docLabel.setText(file.getName());
            }
        });
        
        int row = 0;
        addFormField(personalPanel, gbc, row++, "Full Name *:", nameField);
        addFormField(personalPanel, gbc, row++, "Mobile Number *:", mobileField);
        addFormField(personalPanel, gbc, row++, "Date of Birth:", dobField);
        addFormField(personalPanel, gbc, row++, "Age *:", ageField);
        
        gbc.gridx = 0;
        gbc.gridy = row;
        personalPanel.add(new JLabel("Gender *:"), gbc);
        gbc.gridx = 1;
        personalPanel.add(genderBox, gbc);
        row++;
        
        addFormField(personalPanel, gbc, row++, "Domicile Number *:", domicileField);
        addFormField(personalPanel, gbc, row++, "State From *:", stateFromField);
        addFormField(personalPanel, gbc, row++, "District *:", districtField);
        addFormField(personalPanel, gbc, row++, "Destination State *:", destinationStateField);
        addFormField(personalPanel, gbc, row++, "Job Title *:", jobTitleField);
        addFormField(personalPanel, gbc, row++, "Skill Set *:", skillSetField);
        
        gbc.gridx = 0;
        gbc.gridy = row;
        personalPanel.add(new JLabel("Profile Photo:"), gbc);
        gbc.gridx = 1;
        JPanel photoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        photoPanel.add(uploadPhotoBtn);
        photoPanel.add(photoLabel);
        personalPanel.add(photoPanel, gbc);
        row++;
        
        gbc.gridx = 0;
        gbc.gridy = row;
        personalPanel.add(new JLabel("Niwas Praman Patra:"), gbc);
        gbc.gridx = 1;
        JPanel docPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        docPanel.add(uploadDocBtn);
        docPanel.add(docLabel);
        personalPanel.add(docPanel, gbc);
        
        tabbedPane.addTab("Personal Details", new JScrollPane(personalPanel));
        
        // Guardian Details Tab
        JPanel guardianPanel = new JPanel(new GridBagLayout());
        guardianPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.insets = new Insets(5, 5, 5, 5);
        
        JTextField guardianNameField = new JTextField(20);
        JTextField guardianMobileField = new JTextField(20);
        JTextField guardianRelationField = new JTextField(20);
        JTextField guardianAddressField = new JTextField(20);
        
        int row2 = 0;
        addFormField(guardianPanel, gbc2, row2++, "Guardian Name *:", guardianNameField);
        addFormField(guardianPanel, gbc2, row2++, "Guardian Mobile *:", guardianMobileField);
        addFormField(guardianPanel, gbc2, row2++, "Relation:", guardianRelationField);
        addFormField(guardianPanel, gbc2, row2++, "Guardian Address:", guardianAddressField);
        
        tabbedPane.addTab("Guardian Details", new JScrollPane(guardianPanel));
        
        // Family Details Tab
        JPanel familyPanel = new JPanel(new GridBagLayout());
        familyPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.fill = GridBagConstraints.HORIZONTAL;
        gbc3.insets = new Insets(5, 5, 5, 5);
        
        JCheckBox movingWithFamilyBox = new JCheckBox("Moving with Family");
        movingWithFamilyBox.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        JTextField familyMembersField = new JTextField(20);
        JTextArea familyDetailsArea = new JTextArea(8, 20);
        familyDetailsArea.setLineWrap(true);
        familyDetailsArea.setWrapStyleWord(true);
        familyDetailsArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        JLabel instructionLabel = new JLabel("<html>Enter family details (Name, Age, Gender, Relation)<br>Example: Radha, 28, Female, Wife</html>");
        instructionLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        
        int row3 = 0;
        gbc3.gridx = 0;
        gbc3.gridy = row3++;
        gbc3.gridwidth = 2;
        familyPanel.add(movingWithFamilyBox, gbc3);
        
        gbc3.gridwidth = 1;
        addFormField(familyPanel, gbc3, row3++, "Number of Family Members:", familyMembersField);
        
        gbc3.gridx = 0;
        gbc3.gridy = row3++;
        gbc3.gridwidth = 2;
        familyPanel.add(instructionLabel, gbc3);
        
        gbc3.gridy = row3++;
        familyPanel.add(new JLabel("Family Details:"), gbc3);
        
        gbc3.gridy = row3++;
        familyPanel.add(new JScrollPane(familyDetailsArea), gbc3);
        
        tabbedPane.addTab("Family Details", new JScrollPane(familyPanel));
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Save Worker");
        JButton cancelBtn = new JButton("Cancel");
        
        saveBtn.setBackground(new Color(76, 175, 80));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        cancelBtn.setBackground(new Color(244, 67, 54));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        saveBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String mobile = mobileField.getText().trim();
            String dob = dobField.getText().trim();
            String age = ageField.getText().trim();
            String gender = (String) genderBox.getSelectedItem();
            String domicile = domicileField.getText().trim();
            String stateFrom = stateFromField.getText().trim();
            String district = districtField.getText().trim();
            String destState = destinationStateField.getText().trim();
            String jobTitle = jobTitleField.getText().trim();
            String skillSet = skillSetField.getText().trim();
            String guardianName = guardianNameField.getText().trim();
            String guardianMobile = guardianMobileField.getText().trim();
            String guardianRelation = guardianRelationField.getText().trim();
            String guardianAddress = guardianAddressField.getText().trim();
            boolean movingWithFamily = movingWithFamilyBox.isSelected();
            String familyMembers = familyMembersField.getText().trim();
            String familyDetails = familyDetailsArea.getText().trim();
            
            if (name.isEmpty() || mobile.isEmpty() || age.isEmpty() || domicile.isEmpty() || 
                stateFrom.isEmpty() || district.isEmpty() || destState.isEmpty() || 
                jobTitle.isEmpty() || skillSet.isEmpty() || guardianName.isEmpty() || guardianMobile.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, 
                    "Please fill all required fields marked with *", 
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String workerId = "WRK" + String.format("%03d", workers.size() + 1);
            String regDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
            
            MigrantWorker worker = new MigrantWorker(workerId, name, mobile, dob, age, gender,
                domicile, stateFrom, district, destState, jobTitle, skillSet, guardianName,
                guardianMobile, guardianRelation, guardianAddress, movingWithFamily, familyMembers,
                familyDetails, photoPath[0], docPath[0], regDate);
            workers.add(worker);
            
            JOptionPane.showMessageDialog(dialog, 
                "Worker added successfully!\n\nWorker ID: " + workerId + 
                "\nName: " + name + "\nTotal Workers: " + workers.size(), 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
    
    private void showAddLandlordDialog() {
        JDialog dialog = new JDialog(this, "Add New Landlord", true);
        dialog.setSize(600, 600);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField nameField = new JTextField(20);
        JTextField mobileField = new JTextField(20);
        JTextField propertyField = new JTextField(20);
        JTextField districtField = new JTextField(20);
        JTextField stateField = new JTextField(20);
        JTextField rentField = new JTextField(20);
        JTextField startDateField = new JTextField(20);
        JTextField endDateField = new JTextField(20);
        JTextField workersCountField = new JTextField(20);
        
        int row = 0;
        addFormField(panel, gbc, row++, "Full Name *:", nameField);
        addFormField(panel, gbc, row++, "Mobile Number *:", mobileField);
        addFormField(panel, gbc, row++, "Property Number *:", propertyField);
        addFormField(panel, gbc, row++, "District *:", districtField);
        addFormField(panel, gbc, row++, "State *:", stateField);
        addFormField(panel, gbc, row++, "Monthly Rent *:", rentField);
        addFormField(panel, gbc, row++, "Agreement Start *:", startDateField);
        addFormField(panel, gbc, row++, "Agreement End *:", endDateField);
        addFormField(panel, gbc, row++, "Workers Capacity:", workersCountField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Save Landlord");
        JButton cancelBtn = new JButton("Cancel");
        
        saveBtn.setBackground(new Color(156, 39, 176));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        cancelBtn.setBackground(new Color(244, 67, 54));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        saveBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String mobile = mobileField.getText().trim();
            String property = propertyField.getText().trim();
            String district = districtField.getText().trim();
            String state = stateField.getText().trim();
            String rentStr = rentField.getText().trim();
            String startDate = startDateField.getText().trim();
            String endDate = endDateField.getText().trim();
            String workersCountStr = workersCountField.getText().trim();
            
            if (name.isEmpty() || mobile.isEmpty() || property.isEmpty() || district.isEmpty() || 
                state.isEmpty() || rentStr.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, 
                    "Please fill all required fields", 
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                int rent = Integer.parseInt(rentStr);
                int workersCount = workersCountStr.isEmpty() ? 0 : Integer.parseInt(workersCountStr);
                
                String landlordId = "LND" + String.format("%03d", landlords.size() + 1);
                
                Landlord landlord = new Landlord(landlordId, name, mobile, property, district, 
                    state, rent, startDate, endDate, workersCount);
                landlords.add(landlord);
                
                JOptionPane.showMessageDialog(dialog, 
                    "Landlord added successfully!\n\nLandlord ID: " + landlordId + 
                    "\nName: " + name + "\nTotal Landlords: " + landlords.size(), 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, 
                    "Please enter valid numbers", 
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        
        dialog.add(new JScrollPane(panel));
        dialog.setVisible(true);
    }
    
    private void showLinkDialog() {
        JDialog dialog = new JDialog(this, "Link Worker to Landlord", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel title = new JLabel("Link Migrant Worker to Landlord");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);
        
        String[] workerIds = workers.stream().map(w -> w.workerId + " - " + w.fullName).toArray(String[]::new);
        String[] landlordIds = landlords.stream().map(l -> l.landlordId + " - " + l.fullName).toArray(String[]::new);
        
        JComboBox<String> workerCombo = new JComboBox<>(workerIds);
        JComboBox<String> landlordCombo = new JComboBox<>(landlordIds);
        JTextField moveInDateField = new JTextField(15);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(new JLabel("Select Worker:"), gbc);
        gbc.gridx = 1;
        panel.add(workerCombo, gbc);
        
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(new JLabel("Select Landlord:"), gbc);
        gbc.gridx = 1;
        panel.add(landlordCombo, gbc);
        
        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(new JLabel("Move-in Date:"), gbc);
        gbc.gridx = 1;
        panel.add(moveInDateField, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton linkBtn = new JButton("Link");
        JButton cancelBtn = new JButton("Cancel");
        
        linkBtn.setBackground(new Color(0, 150, 136));
        linkBtn.setForeground(Color.WHITE);
        
        cancelBtn.setBackground(new Color(244, 67, 54));
        cancelBtn.setForeground(Color.WHITE);
        
        linkBtn.addActionListener(e -> {
            if (workerCombo.getSelectedIndex() < 0 || landlordCombo.getSelectedIndex() < 0) {
                JOptionPane.showMessageDialog(dialog, "Please select both worker and landlord");
                return;
            }
            
            MigrantWorker worker = workers.get(workerCombo.getSelectedIndex());
            Landlord landlord = landlords.get(landlordCombo.getSelectedIndex());
            String moveInDate = moveInDateField.getText().trim();
            
            if (moveInDate.isEmpty()) {
                moveInDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
            }
            
            String linkId = "LINK" + String.format("%03d", landlordWorkerLinks.size() + 1);
            landlordWorkerLinks.add(new LandlordWorkerLink(linkId, worker, landlord, moveInDate));
            
            JOptionPane.showMessageDialog(dialog, 
                "Successfully linked!\n\nWorker: " + worker.fullName + 
                "\nLandlord: " + landlord.fullName, 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(linkBtn);
        buttonPanel.add(cancelBtn);
        
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void showLandlordWorkerDashboard() {
        JDialog dialog = new JDialog(this, "Landlord-Worker Registration Dashboard", true);
        dialog.setSize(1300, 700);
        dialog.setLocationRelativeTo(this);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel header = new JLabel("Landlord-Worker Registrations: " + landlordWorkerLinks.size());
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(header, BorderLayout.NORTH);
        
        String[] columns = {"Link ID", "Worker ID", "Worker Name", "Worker Mobile", 
                           "Landlord ID", "Landlord Name", "Landlord Mobile", 
                           "Property", "District", "State", "Move-in Date"};
        
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        styleTable(table);
        
        for (LandlordWorkerLink link : landlordWorkerLinks) {
            model.addRow(new Object[]{
                link.linkId,
                link.worker.workerId,
                link.worker.fullName,
                link.worker.mobile,
                link.landlord.landlordId,
                link.landlord.fullName,
                link.landlord.mobile,
                link.landlord.propertyNo,
                link.landlord.district,
                link.landlord.state,
                link.moveInDate
            });
        }
        
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        
        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.setBorder(BorderFactory.createTitledBorder("Selected Registration Details"));
        JTextArea detailArea = new JTextArea(8, 50);
        detailArea.setEditable(false);
        detailArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        detailPanel.add(new JScrollPane(detailArea), BorderLayout.CENTER);
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                LandlordWorkerLink link = landlordWorkerLinks.get(row);
                
                StringBuilder details = new StringBuilder();
                details.append("=== WORKER DETAILS ===\n");
                details.append("ID: ").append(link.worker.workerId).append("\n");
                details.append("Name: ").append(link.worker.fullName).append("\n");
                details.append("Mobile: ").append(link.worker.mobile).append("\n");
                details.append("Age: ").append(link.worker.age).append(" | Gender: ").append(link.worker.gender).append("\n");
                details.append("Domicile: ").append(link.worker.domicileNo).append("\n");
                details.append("From: ").append(link.worker.district).append(", ").append(link.worker.stateFrom).append("\n");
                details.append("Job: ").append(link.worker.jobTitle).append(" | Skills: ").append(link.worker.skillSet).append("\n");
                details.append("Guardian: ").append(link.worker.guardianName).append(" (").append(link.worker.guardianMobile).append(")\n");
                details.append("Moving with Family: ").append(link.worker.movingWithFamily ? "Yes" : "No").append("\n");
                if (link.worker.movingWithFamily) {
                    details.append("Family Members: ").append(link.worker.familyMembers).append("\n");
                    details.append("Family Details: ").append(link.worker.familyDetails).append("\n");
                }
                details.append("\n=== LANDLORD DETAILS ===\n");
                details.append("ID: ").append(link.landlord.landlordId).append("\n");
                details.append("Name: ").append(link.landlord.fullName).append("\n");
                details.append("Mobile: ").append(link.landlord.mobile).append("\n");
                details.append("Property: ").append(link.landlord.propertyNo).append("\n");
                details.append("Location: ").append(link.landlord.district).append(", ").append(link.landlord.state).append("\n");
                details.append("Monthly Rent: Rs ").append(link.landlord.monthlyRent).append("\n");
                details.append("Agreement: ").append(link.landlord.agreementStart).append(" to ").append(link.landlord.agreementEnd).append("\n");
                details.append("\n=== REGISTRATION INFO ===\n");
                details.append("Link ID: ").append(link.linkId).append("\n");
                details.append("Move-in Date: ").append(link.moveInDate).append("\n");
                
                detailArea.setText(details.toString());
                detailArea.setCaretPosition(0);
            }
        });
        
        mainPanel.add(detailPanel, BorderLayout.SOUTH);
        
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dialog.dispose());
        JPanel btnPanel = new JPanel();
        btnPanel.add(closeBtn);
        mainPanel.add(btnPanel, BorderLayout.EAST);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
    
    private void authenticateAndShow(String type) {
        JPasswordField passwordField = new JPasswordField(15);
        String message = type.equals("workers") ? 
            "Enter password to view Migrant Workers:" : 
            "Enter password to view Landlords:";
        
        Object[] obj = {message, passwordField};
        int option = JOptionPane.showConfirmDialog(this, obj, "Authentication Required", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (option == JOptionPane.OK_OPTION) {
            String password = new String(passwordField.getPassword());
            
            if (type.equals("workers") && password.equals(WORKER_PASSWORD)) {
                showWorkersView();
            } else if (type.equals("landlords") && password.equals(LANDLORD_PASSWORD)) {
                showLandlordsView();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Incorrect Password!\n\nWorkers password: " + WORKER_PASSWORD + 
                    "\nLandlords password: " + LANDLORD_PASSWORD,
                    "Authentication Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showWorkersView() {
        JDialog dialog = new JDialog(this, "Migrant Workers Database", true);
        dialog.setSize(1300, 650);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel header = new JLabel("Total Migrant Workers: " + workers.size());
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(header, BorderLayout.NORTH);
        
        String[] columns = {"Worker ID", "Name", "Mobile", "Age", "Gender", "Domicile", 
                           "State From", "District", "Job", "Guardian", "Guardian Mobile", 
                           "With Family", "Registration"};
        
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        styleTable(table);
        
        for (MigrantWorker w : workers) {
            model.addRow(new Object[]{
                w.workerId, w.fullName, w.mobile, w.age, w.gender, w.domicileNo,
                w.stateFrom, w.district, w.jobTitle, w.guardianName, w.guardianMobile,
                w.movingWithFamily ? "Yes" : "No", w.registrationDate
            });
        }
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dialog.dispose());
        JPanel btnPanel = new JPanel();
        btnPanel.add(closeBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void showLandlordsView() {
        JDialog dialog = new JDialog(this, "Landlords Database", true);
        dialog.setSize(1200, 600);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel header = new JLabel("Total Landlords: " + landlords.size());
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(header, BorderLayout.NORTH);
        
        String[] columns = {"Landlord ID", "Full Name", "Mobile", "Property No", "District", 
                           "State", "Monthly Rent", "Agreement Start", "Agreement End", "Workers Capacity"};
        
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        styleTable(table);
        
        for (Landlord l : landlords) {
            model.addRow(new Object[]{
                l.landlordId, l.fullName, l.mobile, l.propertyNo, l.district, l.state,
                "Rs " + l.monthlyRent, l.agreementStart, l.agreementEnd, l.workersCount
            });
        }
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dialog.dispose());
        JPanel btnPanel = new JPanel();
        btnPanel.add(closeBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void showVerificationDialog() {
        JDialog dialog = new JDialog(this, "Verify Credentials", true);
        dialog.setSize(650, 550);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel titleLabel = new JLabel("Credential Verification System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        JTextArea instructions = new JTextArea(
            "Enter worker details to verify against database.\n" +
            "System will check worker records and landlord linkage."
        );
        instructions.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        instructions.setEditable(false);
        instructions.setOpaque(false);
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);
        gbc.gridy = 1;
        panel.add(instructions, gbc);
        
        JTextField workerNameField = new JTextField(20);
        JTextField domicileIdField = new JTextField(20);
        JTextField stateOriginField = new JTextField(20);
        JTextField landlordNameField = new JTextField(20);
        
        gbc.gridwidth = 1;
        int row = 2;
        addFormField(panel, gbc, row++, "Worker Name:", workerNameField);
        addFormField(panel, gbc, row++, "Domicile Number:", domicileIdField);
        addFormField(panel, gbc, row++, "State of Origin:", stateOriginField);
        addFormField(panel, gbc, row++, "Landlord Name:", landlordNameField);
        
        JTextArea resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        panel.add(new JScrollPane(resultArea), gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton verifyBtn = new JButton("Verify");
        JButton clearBtn = new JButton("Clear");
        JButton closeBtn = new JButton("Close");
        
        verifyBtn.setBackground(new Color(76, 175, 80));
        verifyBtn.setForeground(Color.WHITE);
        verifyBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        clearBtn.setBackground(new Color(33, 150, 243));
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        closeBtn.setBackground(new Color(244, 67, 54));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        verifyBtn.addActionListener(e -> {
            String workerName = workerNameField.getText().trim();
            String domicileId = domicileIdField.getText().trim();
            String stateOrigin = stateOriginField.getText().trim();
            String landlordName = landlordNameField.getText().trim();
            
            if (workerName.isEmpty() || domicileId.isEmpty() || stateOrigin.isEmpty()) {
                resultArea.setText("ERROR: Please fill all worker fields!");
                resultArea.setBackground(new Color(255, 235, 238));
                return;
            }
            
            boolean workerFound = false;
            MigrantWorker foundWorker = null;
            for (MigrantWorker w : workers) {
                if (w.fullName.equalsIgnoreCase(workerName) && 
                    w.domicileNo.equalsIgnoreCase(domicileId) &&
                    w.stateFrom.equalsIgnoreCase(stateOrigin)) {
                    workerFound = true;
                    foundWorker = w;
                    break;
                }
            }
            
            boolean landlordFound = false;
            Landlord foundLandlord = null;
            if (!landlordName.isEmpty()) {
                for (Landlord l : landlords) {
                    if (l.fullName.equalsIgnoreCase(landlordName)) {
                        landlordFound = true;
                        foundLandlord = l;
                        break;
                    }
                }
            }
            
            StringBuilder result = new StringBuilder();
            
            if (workerFound) {
                result.append("VERIFICATION SUCCESSFUL!\n\n");
                result.append("Worker Details:\n");
                result.append("ID: ").append(foundWorker.workerId).append("\n");
                result.append("Name: ").append(foundWorker.fullName).append("\n");
                result.append("Mobile: ").append(foundWorker.mobile).append("\n");
                result.append("State: ").append(foundWorker.stateFrom).append("\n");
                result.append("Job: ").append(foundWorker.jobTitle).append("\n");
                result.append("Guardian: ").append(foundWorker.guardianName).append("\n");
                
                if (landlordFound) {
                    result.append("\nLandlord Verified:\n");
                    result.append("Name: ").append(foundLandlord.fullName).append("\n");
                    result.append("Mobile: ").append(foundLandlord.mobile).append("\n");
                    result.append("Location: ").append(foundLandlord.district).append(", ").append(foundLandlord.state).append("\n");
                } else if (!landlordName.isEmpty()) {
                    result.append("\nLandlord not found!");
                }
                
                resultArea.setBackground(new Color(232, 245, 233));
                
                String verificationId = "VER" + String.format("%03d", verifications.size() + 1);
                String timestamp = new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date());
                verifications.add(new VerificationRecord(verificationId, workerName, domicileId,
                    stateOrigin, landlordName, "VERIFIED", timestamp, 100));
                
            } else {
                result.append("VERIFICATION FAILED!\n\n");
                result.append("Worker not found in database.\n");
                result.append("Searched Name: ").append(workerName).append("\n");
                result.append("Domicile: ").append(domicileId).append("\n");
                result.append("State: ").append(stateOrigin).append("\n\n");
                result.append("ALERT SENT TO POLICE!\n");
                
                resultArea.setBackground(new Color(255, 235, 238));
                
                String verificationId = "VER" + String.format("%03d", verifications.size() + 1);
                String timestamp = new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date());
                verifications.add(new VerificationRecord(verificationId, workerName, domicileId,
                    stateOrigin, landlordName, "FAILED", timestamp, 0));
                
                Toolkit.getDefaultToolkit().beep();
            }
            
            resultArea.setText(result.toString());
        });
        
        clearBtn.addActionListener(e -> {
            workerNameField.setText("");
            domicileIdField.setText("");
            stateOriginField.setText("");
            landlordNameField.setText("");
            resultArea.setText("");
            resultArea.setBackground(Color.WHITE);
        });
        
        closeBtn.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(verifyBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(closeBtn);
        
        gbc.gridy = row;
        panel.add(buttonPanel, gbc);
        
        dialog.add(new JScrollPane(panel));
        dialog.setVisible(true);
    }
    
    private void showFullDashboard() {
        JDialog dialog = new JDialog(this, "Full Dashboard", true);
        dialog.setSize(1400, 800);
        dialog.setLocationRelativeTo(this);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        tabbedPane.addTab("Workers (" + workers.size() + ")", createFullWorkerPanel());
        tabbedPane.addTab("Landlords (" + landlords.size() + ")", createFullLandlordPanel());
        tabbedPane.addTab("Worker-Landlord Links (" + landlordWorkerLinks.size() + ")", createLinksPanel());
        tabbedPane.addTab("Verifications (" + verifications.size() + ")", createFullVerificationPanel());
        tabbedPane.addTab("Police Alerts", createPoliceAlertsPanel());
        
        dialog.add(tabbedPane);
        dialog.setVisible(true);
    }
    
    private JPanel createFullWorkerPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] columns = {"Worker ID", "Name", "Mobile", "Age", "Gender", "Domicile", 
                           "State", "Job", "Guardian", "Family", "Reg Date"};
        
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        styleTable(table);
        
        for (MigrantWorker w : workers) {
            model.addRow(new Object[]{
                w.workerId, w.fullName, w.mobile, w.age, w.gender, w.domicileNo,
                w.stateFrom, w.jobTitle, w.guardianName, 
                w.movingWithFamily ? "Yes (" + w.familyMembers + ")" : "No", w.registrationDate
            });
        }
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createFullLandlordPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] columns = {"Landlord ID", "Name", "Mobile", "Property", "District", 
                           "State", "Rent", "Agreement", "Capacity"};
        
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        styleTable(table);
        
        for (Landlord l : landlords) {
            model.addRow(new Object[]{
                l.landlordId, l.fullName, l.mobile, l.propertyNo, l.district, l.state,
                "Rs " + l.monthlyRent, l.agreementStart + " to " + l.agreementEnd, l.workersCount
            });
        }
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createLinksPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] columns = {"Link ID", "Worker", "Worker Mobile", "Landlord", 
                           "Landlord Mobile", "Property", "Location", "Move-in Date"};
        
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        styleTable(table);
        
        for (LandlordWorkerLink link : landlordWorkerLinks) {
            model.addRow(new Object[]{
                link.linkId, link.worker.fullName, link.worker.mobile,
                link.landlord.fullName, link.landlord.mobile,
                link.landlord.propertyNo, 
                link.landlord.district + ", " + link.landlord.state,
                link.moveInDate
            });
        }
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createFullVerificationPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] columns = {"Verification ID", "Worker Name", "Domicile ID", "State", 
                           "Landlord", "Status", "Time", "Score"};
        
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        styleTable(table);
        
        for (VerificationRecord v : verifications) {
            model.addRow(new Object[]{
                v.verificationId, v.workerName, v.domicileId, v.stateOrigin,
                v.landlordName, v.status, v.verificationTime, v.matchScore + "%"
            });
        }
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createPoliceAlertsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea alertArea = new JTextArea();
        alertArea.setEditable(false);
        alertArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        alertArea.setBackground(new Color(255, 245, 245));
        
        StringBuilder alerts = new StringBuilder();
        alerts.append("POLICE ALERT DASHBOARD\n");
        alerts.append("=".repeat(80)).append("\n\n");
        
        int alertCount = 0;
        for (VerificationRecord vr : verifications) {
            if ("FAILED".equals(vr.status)) {
                alertCount++;
                alerts.append("ALERT #").append(alertCount).append("\n");
                alerts.append("Time: ").append(vr.verificationTime).append("\n");
                alerts.append("Worker Name: ").append(vr.workerName).append("\n");
                alerts.append("Domicile ID: ").append(vr.domicileId).append("\n");
                alerts.append("State: ").append(vr.stateOrigin).append("\n");
                alerts.append("Landlord: ").append(vr.landlordName).append("\n");
                alerts.append("Status: VERIFICATION FAILED\n");
                alerts.append("Reason: No matching record\n");
                alerts.append("Action: Investigation needed\n");
                alerts.append("-".repeat(80)).append("\n\n");
            }
        }
        
        if (alertCount == 0) {
            alerts.append("No alerts. All verifications passed.\n");
        }
        
        alertArea.setText(alerts.toString());
        alertArea.setCaretPosition(0);
        
        panel.add(new JScrollPane(alertArea), BorderLayout.CENTER);
        return panel;
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String label, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(lbl, gbc);
        
        gbc.gridx = 1;
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(field, gbc);
    }
    
    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        table.setSelectionBackground(new Color(220, 237, 255));
        table.setGridColor(new Color(220, 220, 220));
        
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setBackground(new Color(63, 81, 181));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
    }
    
    private void addSampleData() {
        workers.add(new MigrantWorker("WRK001", "Rajesh Kumar", "9876543210", "15/03/1985", "39", "Male",
            "BH2024001", "Bihar", "Patna", "Delhi", "Construction Worker", "Mason, Helper",
            "Ram Kumar", "9876543211", "Father", "Village Patna", true, "3",
            "Radha Devi, 35, Female, Wife\nRaj Kumar, 10, Male, Son\nPriya, 7, Female, Daughter",
            "", "", "01/01/2024"));
        
        workers.add(new MigrantWorker("WRK002", "Priya Sharma", "9876543220", "22/07/1992", "32", "Female",
            "UP2024002", "Uttar Pradesh", "Lucknow", "Mumbai", "Factory Worker", "Machine Operator",
            "Suresh Sharma", "9876543221", "Father", "Lucknow City", false, "",
            "", "", "", "05/01/2024"));
        
        landlords.add(new Landlord("LND001", "Suresh Patel", "9988776655", "B-204", "Delhi", "Delhi",
            8000, "01/01/2024", "31/12/2024", 5));
        
        landlords.add(new Landlord("LND002", "Meena Gupta", "9988776656", "A-101", "Mumbai", "Maharashtra",
            12000, "05/01/2024", "05/01/2025", 3));
        
        landlordWorkerLinks.add(new LandlordWorkerLink("LINK001", workers.get(0), landlords.get(0), "02/01/2024"));
        
        verifications.add(new VerificationRecord("VER001", "Rajesh Kumar", "BH2024001",
            "Bihar", "Suresh Patel", "VERIFIED", "02/01/2024 10:30 AM", 100));
        verifications.add(new VerificationRecord("VER002", "Priya Sharma", "UP2024002",
            "Uttar Pradesh", "Meena Gupta", "VERIFIED", "06/01/2024 02:15 PM", 100));
        verifications.add(new VerificationRecord("VER003", "Unknown Worker", "JH2024999",
            "Jharkhand", "Vijay Reddy", "FAILED", "10/01/2024 11:45 AM", 0));
    }
    
    class MigrantWorker {
        String workerId, fullName, mobile, dob, age, gender, domicileNo;
        String stateFrom, district, destinationState, jobTitle, skillSet;
        String guardianName, guardianMobile, guardianRelation, guardianAddress;
        boolean movingWithFamily;
        String familyMembers, familyDetails;
        String photoPath, documentPath, registrationDate;
        
        MigrantWorker(String workerId, String fullName, String mobile, String dob, String age,
                     String gender, String domicileNo, String stateFrom, String district,
                     String destinationState, String jobTitle, String skillSet, String guardianName,
                     String guardianMobile, String guardianRelation, String guardianAddress,
                     boolean movingWithFamily, String familyMembers, String familyDetails,
                     String photoPath, String documentPath, String registrationDate) {
            this.workerId = workerId;
            this.fullName = fullName;
            this.mobile = mobile;
            this.dob = dob;
            this.age = age;
            this.gender = gender;
            this.domicileNo = domicileNo;
            this.stateFrom = stateFrom;
            this.district = district;
            this.destinationState = destinationState;
            this.jobTitle = jobTitle;
            this.skillSet = skillSet;
            this.guardianName = guardianName;
            this.guardianMobile = guardianMobile;
            this.guardianRelation = guardianRelation;
            this.guardianAddress = guardianAddress;
            this.movingWithFamily = movingWithFamily;
            this.familyMembers = familyMembers;
            this.familyDetails = familyDetails;
            this.photoPath = photoPath;
            this.documentPath = documentPath;
            this.registrationDate = registrationDate;
        }
    }
    
    class Landlord {
        String landlordId, fullName, mobile, propertyNo, district, state;
        int monthlyRent, workersCount;
        String agreementStart, agreementEnd;
        
        Landlord(String landlordId, String fullName, String mobile, String propertyNo,
                String district, String state, int monthlyRent, String agreementStart,
                String agreementEnd, int workersCount) {
            this.landlordId = landlordId;
            this.fullName = fullName;
            this.mobile = mobile;
            this.propertyNo = propertyNo;
            this.district = district;
            this.state = state;
            this.monthlyRent = monthlyRent;
            this.agreementStart = agreementStart;
            this.agreementEnd = agreementEnd;
            this.workersCount = workersCount;
        }
    }
    
    class VerificationRecord {
        String verificationId, workerName, domicileId, stateOrigin;
        String landlordName, status, verificationTime;
        int matchScore;
        
        VerificationRecord(String verificationId, String workerName, String domicileId,
                          String stateOrigin, String landlordName, String status,
                          String verificationTime, int matchScore) {
            this.verificationId = verificationId;
            this.workerName = workerName;
            this.domicileId = domicileId;
            this.stateOrigin = stateOrigin;
            this.landlordName = landlordName;
            this.status = status;
            this.verificationTime = verificationTime;
            this.matchScore = matchScore;
        }
    }
    
    class LandlordWorkerLink {
        String linkId;
        MigrantWorker worker;
        Landlord landlord;
        String moveInDate;
        
        LandlordWorkerLink(String linkId, MigrantWorker worker, Landlord landlord, String moveInDate) {
            this.linkId = linkId;
            this.worker = worker;
            this.landlord = landlord;
            this.moveInDate = moveInDate;
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MigrantWorkerDashboard();
        });
    }
}
