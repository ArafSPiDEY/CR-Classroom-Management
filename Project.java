import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.io.*;
import java.util.*;

public class Project {

    static String[][] data = new String[7][10];
    static DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column != 0;
        }
    };

    static void readFile(String fileName) {
        try {
            File f = new File(fileName);
            f.createNewFile();
            while (model.getRowCount() > 0) {
                model.removeRow(0);
            }
            Scanner s = new Scanner(f);
            if (!s.hasNextLine()) { // Empty File
                // 1st Column = Setting Room Numbers
                data[0][0] = "201";
                data[1][0] = "202";
                data[2][0] = "203";
                data[3][0] = "101";
                data[4][0] = "102";
                data[5][0] = "103";
                data[6][0] = "104";

                // Rest Rows
                for (int i = 0; i < 7; i++) {
                    for (int j = 1; j < 10; j++) {
                        data[i][j] = null;
                    }
                }
            } else { // Already Exists
                int i = 0;
                while (s.hasNextLine()) {
                    String line = s.nextLine();
                    String[] words = line.split(",");
                    for (int j = 0; j < words.length; j++) {
                        data[i][j] = words[j];
                    }
                    for (int j = words.length; j < 10; j++) {
                        data[i][j] = null;
                    }
                    i++;
                } s.close();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File Read Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        Project.addData(model);
    }

    static void writeFile(String fileName) {
        try {
            // JTable to data
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 10; j++) {
                    data[i][j] = (String) model.getValueAt(i,j);
                }
            }
            // Write to File
            FileWriter w = new FileWriter(fileName);
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 10; j++) {
                    if (data[i][j] == null) w.write("");
                    else w.write(data[i][j]);
                    w.write(",");
                }
                if (i < 6) w.write("\n");
            } w.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File Write Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    static void addData(DefaultTableModel model) {
        model.addRow(data[0]);
        model.addRow(data[1]);
        model.addRow(data[2]);
        model.addRow(data[3]);
        model.addRow(data[4]);
        model.addRow(data[5]);
        model.addRow(data[6]);
    }

    public static void main(String[] args) {

        // JFrame
        JFrame frame = new JFrame("CR Classroom Management Software");
        frame.setLocation(100,75);
        frame.setLayout(new BorderLayout(0,0));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 1st Row = Setting Column Names
        String[] col1 = new String[10];
        col1[0] = "Room No";
        for (int i = 1; i < 10; i++) {
            col1[i] = "Period " + i;
        }
        model.setColumnIdentifiers(col1);
        readFile("SAT.txt");

        String[] days = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday"};
        JComboBox<String> comboBox = new JComboBox<>(days);
        comboBox.setFocusable(false);
        comboBox.setPreferredSize(new Dimension(100, 25));
        comboBox.addActionListener(e -> {
            if (e.getSource() == comboBox) {
                switch (comboBox.getSelectedIndex()) {
                    case 0: // SATURDAY
                        readFile("SAT.txt");
                    break;
                    case 1: // SUNDAY
                        readFile("SUN.txt");
                    break;
                    case 2: // MONDAY
                        readFile("MON.txt");
                    break;
                    case 3: // TUESDAY
                        readFile("TUE.txt");
                    break;
                    case 4: // WEDNESDAY
                        readFile("WED.txt");
                    break;
                    default: 
                        JOptionPane.showMessageDialog(frame, "Invalid Option!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Button
        JButton button = new JButton("Save");
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(70,25));
        button.addActionListener(e -> {
            if (e.getSource() == button) {
                switch (comboBox.getSelectedIndex()) {
                    case 0: // Sat
                        writeFile("SAT.txt");
                    break;
                    case 1:
                        writeFile("SUN.txt");
                    break;
                    case 2:
                        writeFile("MON.txt");
                    break;
                    case 3:
                        writeFile("TUE.txt");
                    break;
                    case 4:
                        writeFile("WED.txt");
                    break;
                    default: 
                        JOptionPane.showMessageDialog(frame, "Invalid Option!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                comboBox.setEnabled(true);
            }
        });

        // JTable
        JTable table = new JTable(model);
        table.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(table.getPreferredSize().width + 3, table.getRowHeight() * table.getRowCount() + table.getTableHeader().getPreferredSize().height + 3));
        model.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                comboBox.setEnabled(false);
            }
        });
        class CustomCellRenderer extends DefaultTableCellRenderer {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null && !value.toString().trim().isEmpty()) {
                    String text = value.toString();
                    if (text.equalsIgnoreCase("19A")) {
                        cell.setBackground(new Color(0xff1a1a)); // Dark Red
                    } else if (text.equalsIgnoreCase("19B")) {
                        cell.setBackground(new Color(0xff4d4d)); // Medium Red
                    } else if (text.equalsIgnoreCase("19C")) {
                        cell.setBackground(new Color(0xff6666)); // Light Red
                    } else if (text.equalsIgnoreCase("20A")) {
                        cell.setBackground(new Color(0x33cc33)); // Dark Green
                    } else if (text.equalsIgnoreCase("20B")) {
                        cell.setBackground(new Color(0x66ff66)); // Medium Green
                    } else if (text.equalsIgnoreCase("20C")) {
                        cell.setBackground(new Color(0x99ff99)); // Light Green
                    } else if (text.equalsIgnoreCase("21A")) {
                        cell.setBackground(new Color(0xff6600)); // Dark Orange
                    } else if (text.equalsIgnoreCase("21B")) {
                        cell.setBackground(new Color(0xff9966)); // Medium Orange
                    } else if (text.equalsIgnoreCase("21C")) {
                        cell.setBackground(new Color(0xffcc99)); // Light Orange
                    } else if (text.equalsIgnoreCase("22A")) {
                        cell.setBackground(new Color(0x3366cc)); // Dark Blue
                    } else if (text.equalsIgnoreCase("22B")) {
                        cell.setBackground(new Color(0x6699ff)); // Medium Blue
                    } else if (text.equalsIgnoreCase("22C")) {
                        cell.setBackground(new Color(0x99ccff)); // Light Blue
                    } else {
                        cell.setBackground(new Color(0xffffff)); // Default White
                    }
                } else {
                    cell.setBackground(new Color(0xffffff)); // Default White
                }
                return cell;
            }
        }
        CustomCellRenderer cell = new CustomCellRenderer();
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cell);
        }
        cell.setHorizontalAlignment(SwingConstants.CENTER);
        cell.setVerticalAlignment(SwingConstants.CENTER);

        // Panel for ComboBox, Button
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        panel1.setPreferredSize(new Dimension(comboBox.getPreferredSize().width + button.getPreferredSize().width + 30,
                                                Math.max(comboBox.getPreferredSize().height, button.getPreferredSize().height) + 20));
        panel1.add(comboBox);
        panel1.add(button);
        // panel1.setBackground(Color.BLUE);

        // Panel for JTable
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));

        panel2.add(scroll);
        // panel2.setBackground(Color.RED);

        frame.add(panel1, BorderLayout.NORTH);
        frame.add(panel2);
        frame.pack();
        frame.setVisible(true);
    }
}