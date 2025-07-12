package trial1;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class TicketSummaryGUI extends JFrame {

    private int movie_id;
    private String username;
    private List<String> bookedSeats;
    private Map<String, String> movieDetails; // To hold movie info from DB

    public TicketSummaryGUI(int movie_id, String username, List<String> bookedSeats) {
        this.movie_id = movie_id;
        this.username = username;
        this.bookedSeats = bookedSeats;
        this.movieDetails = fetchMovieDetails(movie_id);

        setTitle("Booking Confirmation");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Header label with custom font and color
        JLabel headerLabel = new JLabel("Booking Summary");
        headerLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setForeground(new Color(0, 102, 204));
        add(headerLabel, BorderLayout.NORTH);

        // Text area for details
        JTextArea ticketDetails = new JTextArea();
        ticketDetails.setEditable(false);
        ticketDetails.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 14));
        ticketDetails.setBackground(new Color(245, 245, 245));
        ticketDetails.setMargin(new Insets(10, 10, 10, 10));

        // Build details string
        StringBuilder detailsBuilder = new StringBuilder();
        detailsBuilder.append("Username: ").append(username).append("\n");
        detailsBuilder.append("Movie Title: ").append(movieDetails.getOrDefault("title", "N/A")).append("\n");
        detailsBuilder.append("Genre: ").append(movieDetails.getOrDefault("genre", "N/A")).append("\n");
        detailsBuilder.append("Language: ").append(movieDetails.getOrDefault("language", "N/A")).append("\n");
        detailsBuilder.append("Duration: ").append(movieDetails.getOrDefault("duration", "N/A")).append("\n");
        detailsBuilder.append("Showtime: ").append(movieDetails.getOrDefault("showtime", "N/A")).append("\n");
        detailsBuilder.append("Location: ").append(movieDetails.getOrDefault("location", "N/A")).append("\n");
        detailsBuilder.append("Seats Booked: ").append(String.join(", ", bookedSeats)).append("\n");

        ticketDetails.setText(detailsBuilder.toString());

        add(new JScrollPane(ticketDetails), BorderLayout.CENTER);

        JButton downloadButton = new JButton("Download E-Ticket (PDF)");
downloadButton.setBackground(new Color(0, 102, 204));
downloadButton.setForeground(Color.WHITE);
downloadButton.setFocusPainted(false);
downloadButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
downloadButton.addActionListener(e -> generatePDF());

JButton exitButton = new JButton("Exit");
exitButton.setBackground(Color.RED);
exitButton.setForeground(Color.WHITE);
exitButton.setFocusPainted(false);
exitButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
exitButton.addActionListener(e -> System.exit(0));

JPanel buttonPanel = new JPanel();
buttonPanel.add(downloadButton);
buttonPanel.add(exitButton);
add(buttonPanel, BorderLayout.SOUTH);


    
    }

private Map<String, String> fetchMovieDetails(int movie_id) {
    Map<String, String> details = new HashMap<>();
    System.out.println(movie_id);
    // Set default values
    details.put("title", "Unknown");
    details.put("genre", "Unknown");
    details.put("language", "Unknown");
    details.put("duration", "Unknown");
    details.put("showtime", "Unknown");   // 'time' column mapped to 'showtime'
    details.put("location", "Unknown");

    String url = "jdbc:mysql://localhost:3300/movie_db"; // your DB URL
    String user = "root";
    String password = "";

String query = "SELECT * FROM movies_table WHERE movie_id = ?";

    try (Connection conn = DriverManager.getConnection(url, user, password);
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, movie_id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            details.put("title", rs.getString("title"));
            details.put("genre", rs.getString("genre"));
            details.put("language", rs.getString("language"));
            details.put("duration", rs.getString("duration"));
            details.put("showtime", rs.getString("time"));  // column 'time' mapped to key 'showtime'
            details.put("location", rs.getString("location"));
        }

    } catch (SQLException e) {
        System.err.println("Error fetching movie details: " + e.getMessage());
    }

    return details;
}


    private void generatePDF() {
        Document document = new Document();
        try {
            String filename = "E-Ticket_" + username + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            // Fonts
        com.itextpdf.text.Font titleFont = com.itextpdf.text.FontFactory.getFont(com.itextpdf.text.FontFactory.HELVETICA_BOLD, 20, com.itextpdf.text.BaseColor.BLUE);
com.itextpdf.text.Font headerFont = com.itextpdf.text.FontFactory.getFont(com.itextpdf.text.FontFactory.HELVETICA_BOLD, 14);
com.itextpdf.text.Font normalFont = com.itextpdf.text.FontFactory.getFont(com.itextpdf.text.FontFactory.HELVETICA, 12);
            Paragraph title = new Paragraph("Movie Ticket Confirmation", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(Chunk.NEWLINE);

            // Add movie details
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            addCellToTable(table, "Username:", headerFont);
            addCellToTable(table, username, normalFont);

            addCellToTable(table, "Movie Title:", headerFont);
            addCellToTable(table, movieDetails.getOrDefault("title", "N/A"), normalFont);

            addCellToTable(table, "Genre:", headerFont);
            addCellToTable(table, movieDetails.getOrDefault("genre", "N/A"), normalFont);
            
            addCellToTable(table, "Language:", headerFont);
            addCellToTable(table, movieDetails.getOrDefault("language", "N/A"), normalFont);

            addCellToTable(table, "Duration:", headerFont);
            addCellToTable(table, movieDetails.getOrDefault("duration", "N/A"), normalFont);

            addCellToTable(table, "Showtime:", headerFont);
            addCellToTable(table, movieDetails.getOrDefault("showtime", "N/A"), normalFont);
            
            addCellToTable(table, "Location:", headerFont);
            addCellToTable(table, movieDetails.getOrDefault("location", "N/A"), normalFont);

            addCellToTable(table, "Seats Booked:", headerFont);
            addCellToTable(table, String.join(", ", bookedSeats), normalFont);

            document.add(table);

            document.add(new Paragraph("Thank you for booking with us!", normalFont));

            JOptionPane.showMessageDialog(this, "E-Ticket downloaded successfully as " + filename);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error generating PDF: " + e.getMessage());
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

private void addCellToTable(PdfPTable table, String text, com.itextpdf.text.Font font) {
    PdfPCell cell = new PdfPCell(new Phrase(text, font));
    cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);

    table.addCell(cell);
}

}
