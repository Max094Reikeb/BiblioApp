import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookRegister extends JFrame { 

    private JTextField title;
    private JTextField authorFirstName;
    private JTextField authorLastName;
    private JTextField description;
    private JTextField publicationYear;
    private JTextField column;
    private JTextField row;
    private JTextField pathImage;
    private JTextField borrowed;
    private JButton submitButton;

    public BookRegister() {

        setTitle("Ajout de livres");
        setSize(400, 350);  // Correction de setSize
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(9, 2, 10, 10));

        // Création des Labels et Champs de saisie
        add(new JLabel("Titre : "));
        title = new JTextField();
        add(title);

        add(new JLabel("Prénom de l'auteur : "));
        authorFirstName = new JTextField();
        add(authorFirstName);

        add(new JLabel("Nom de l'auteur : "));
        authorLastName = new JTextField();
        add(authorLastName);

        add(new JLabel("Description : "));
        description = new JTextField();
        add(description);

        add(new JLabel("Année de publication : "));
        publicationYear = new JTextField();
        add(publicationYear);

        add(new JLabel("Colonne : "));
        column = new JTextField();
        add(column);

        add(new JLabel("Rangée : "));
        row = new JTextField();
        add(row);

        add(new JLabel("Chemin de l'image : "));
        pathImage = new JTextField();
        add(pathImage);

        // Bouton d'inscription
        submitButton = new JButton("Ajouter un livre");
        add(submitButton);
        add(new JLabel()); // Espace vide pour alignement

        // Action du bouton
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookTitle = title.getText();
                String firstName = authorFirstName.getText();
                String lastName = authorLastName.getText();

                JOptionPane.showMessageDialog(null, "Livre ajouté : " + bookTitle + " par " + firstName + " " + lastName);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new BookRegister();
    }
}
