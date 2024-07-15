import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class HospitalityManagementSystem {
    private JFrame frame;
    private JTextField guestNameField;
    private JTextField roomNumberField;
    private JTextField checkInDateField;
    private JTextField checkOutDateField;
    private JButton bookRoomButton;
    private JButton viewBookingsButton;
    private JButton addGuestButton;
    private JButton addRoomButton;

    private Connection conn;

    public HospitalityManagementSystem() {
        createGUI();
        connectToDatabase();
    }

    private void createGUI() {
        frame = new JFrame("Hospitality Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel guestNameLabel = new JLabel("Guest Name:");
        guestNameField = new JTextField(20);
        panel.add(guestNameLabel);
        panel.add(guestNameField);

        JLabel roomNumberLabel = new JLabel("Room Number:");
        roomNumberField = new JTextField(20);
        panel.add(roomNumberLabel);
        panel.add(roomNumberField);

        JLabel checkInDateLabel = new JLabel("Check-in Date:");
        checkInDateField = new JTextField(20);
        panel.add(checkInDateLabel);
        panel.add(checkInDateField);

        JLabel checkOutDateLabel = new JLabel("Check-out Date:");
        checkOutDateField = new JTextField(20);
        panel.add(checkOutDateLabel);
        panel.add(checkOutDateField);

        bookRoomButton = new JButton("Book Room");
        bookRoomButton.addActionListener(new BookRoomListener());
        panel.add(bookRoomButton);

        viewBookingsButton = new JButton("View Bookings");
        viewBookingsButton.addActionListener(new ViewBookingsListener());
        panel.add(viewBookingsButton);

        addGuestButton = new JButton("Add Guest");
        addGuestButton.addActionListener(new AddGuestListener());
        panel.add(addGuestButton);

        addRoomButton = new JButton("Add Room");
        addRoomButton.addActionListener(new AddRoomListener());
        panel.add(addRoomButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // for MySQL
            String url = "jdbc:mysql://localhost:3306/hospitality_management";
            String username = "root";
            String password = "password";
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL error!");
            e.printStackTrace();
        }
    }

    private class BookRoomListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String guestName = guestNameField.getText();
            String roomNumber = roomNumberField.getText();
            String checkInDate = checkInDateField.getText();
            String checkOutDate = checkOutDateField.getText();

            try {
                Statement stmt = conn.createStatement();
                String query = "INSERT INTO bookings (guest_name, room_number, check_in_date, check_out_date) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, guestName);
                pstmt.setString(2, roomNumber);
                pstmt.setString(3, checkInDate);
                pstmt.setString(4, checkOutDate);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Room booked successfully!");
            } catch (SQLException ex) {
                System.out.println("SQL error!");
                ex.printStackTrace();
            }
        }
    }

    private class ViewBookingsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Statement stmt = conn.createStatement();
                String query = "SELECT * FROM bookings";
                ResultSet rs = stmt.executeQuery(query);

                String bookings = "";
                while (rs.next()) {
                    String guestName = rs.getString("guest_name");
                    String roomNumber = rs.getString("room_number");
                    String checkInDate = rs.getString("check_in_date");
                    String checkOutDate = rs.getString("check_out_date");
                    bookings += "Guest Name: " + guestName + ", Room Number: " + roomNumber + ", Check-in Date: " + checkInDate + ", Check-out Date: " + checkOutDate + "\n";
                }

                JOptionPane.showMessageDialog(frame, bookings);
            } catch (SQLException ex) {
                System.out.println("SQL error!");
                ex.printStackTrace();
            }
        }
    }

    private class AddGuestListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String guestName = guestNameField.getText();

            try {
                Statement stmt = conn.createStatement();
                String query = "INSERT INTO guests (name) VALUES (?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, guestName);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Guest added successfully!");
            } catch (SQLException ex) {
                System.out.println("SQL error!");
                ex.printStackTrace();
            }
        }
    }

    private class AddRoomListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String roomNumber = roomNumberField.getText();

            try {
                Statement stmt = conn.createStatement();
                String query = "INSERT INTO rooms (room_number) VALUES (?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, roomNumber);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Room added successfully!");
            } catch (SQLException ex) {
                System.out.println("SQL error!");
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new HospitalityManagementSystem();
    }
}
