import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ProductApp extends JFrame {
    private JTextField txtCustomerName, txtPrice;
    private JComboBox<String> cmbProduct;
    private JButton btnProcess;

    public ProductApp() {
        setTitle("Product App");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Menu Bar 
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");

        JMenuItem itemProcess = new JMenuItem("Process");
        JMenuItem itemClear = new JMenuItem("Clear");
        JMenuItem itemExit = new JMenuItem("Exit");

        menuFile.add(itemProcess);
        menuFile.add(itemClear);
        menuFile.addSeparator();
        menuFile.add(itemExit);
        menuBar.add(menuFile);
        setJMenuBar(menuBar);

        // Customer Name Label & Text Field
        JLabel lblCustomer = new JLabel("Customer Name:");
        lblCustomer.setBounds(30, 30, 120, 25);
        add(lblCustomer);

        txtCustomerName = new JTextField();
        txtCustomerName.setBounds(160, 30, 180, 25);
        txtCustomerName.setText("Joe Bloggs"); // Default from screenshot
        add(txtCustomerName);

        // Select Product Label & Combo Box
        JLabel lblProduct = new JLabel("Select Product:");
        lblProduct.setBounds(30, 70, 120, 25);
        add(lblProduct);

        String[] products = {
                "Cozy Nights Monarch",
                "Restonic Barbados",
                "Sealy Phuket"
        };
        cmbProduct = new JComboBox<>(products);
        cmbProduct.setBounds(160, 70, 180, 25);
        add(cmbProduct);

        // Enter Price Label & Text Field
        JLabel lblPrice = new JLabel("Enter Price:");
        lblPrice.setBounds(30, 110, 120, 25);
        add(lblPrice);

        txtPrice = new JTextField("3500");
        txtPrice.setBounds(160, 110, 180, 25);
        add(txtPrice);
        
        btnProcess = new JButton("PROCESS");
        btnProcess.setBounds(160, 160, 180, 30);
        add(btnProcess);

        // Action Listeners for Process (Button & Menu Item)
        ActionListener processAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processSalesData();
            }
        };

        btnProcess.addActionListener(processAction);
        itemProcess.addActionListener(processAction);

        // Clear Menu Item Action 
        itemClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtCustomerName.setText("");
                cmbProduct.setSelectedIndex(0);
                txtPrice.setText("");
            }
        });

        // Exit Menu Item Action 
        itemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void processSalesData() {
        try {
            String customerName = txtCustomerName.getText();
            String selectedProduct = (String) cmbProduct.getSelectedItem();
            double price = Double.parseDouble(txtPrice.getText());

            // Use ProductCalculations class
            ProductCalculations calculations = new ProductCalculations(price);
            double vat = calculations.GetVat();
            double commission = calculations.GetCommission();
            double total = calculations.GetTotal();
            
            String reportMessage = "Customer Name: " + customerName + "\n" +
                    "Product: " + selectedProduct + "\n" +
                    "VAT: R " + vat + "\n" +
                    "COMMISSION: R " + commission + "\n" +
                    "TOTAL: R " + total;

            JOptionPane.showMessageDialog(this, reportMessage, "Message", JOptionPane.INFORMATION_MESSAGE);

            // Save information to data.txt file (Q.2.3 requirements)
            try (PrintWriter writer = new PrintWriter(new FileWriter("data.txt", true))) {
                writer.println("DATA LOG");
                writer.println("**************************************************");
                writer.println("Customer Name: " + customerName);
                writer.println("Product: " + selectedProduct);
                writer.println("VAT: R " + vat);
                writer.println("COMMISSION: R " + commission);
                writer.println("TOTAL: R " + total);
                writer.println("**************************************************");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving to file: " + ex.getMessage());
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric price.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProductApp().setVisible(true);
            }
        });
    }
}

