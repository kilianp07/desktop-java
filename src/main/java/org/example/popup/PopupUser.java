package org.example.popup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.example.model.User;
import org.example.mongoConnect.MongoClientConnection;

public class PopupUser {

    private JFrame frame;
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField dateNaissanceField;
    private JComboBox<String> sexeComboBox;

    public PopupUser() {
        frame = new JFrame("Formulaire");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(5, 2));

        JLabel nomLabel = new JLabel("Nom:");
        nomField = new JTextField();

        JLabel prenomLabel = new JLabel("Prénom:");
        prenomField = new JTextField();

        JLabel dateNaissanceLabel = new JLabel("Date de naissance:");
        dateNaissanceField = new JTextField();

        JLabel sexeLabel = new JLabel("Sexe:");
        String[] sexeOptions = { "Masculin", "Féminin" };
        sexeComboBox = new JComboBox<>(sexeOptions);
        JButton enregistrerButton = new JButton("Enregistrer");
        enregistrerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String dateNaissanceText = dateNaissanceField.getText();
                String sexe = (String) sexeComboBox.getSelectedItem();

                // Validate fields
                if (nom.isEmpty() || prenom.isEmpty() || dateNaissanceText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Veuillez remplir tous les champs obligatoires.", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Conversion de la date de naissance en objet Date
                Date dateNaissance = null;

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    dateNaissance = dateFormat.parse(dateNaissanceText);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Veuillez saisir la date de naissance au format dd/MM/yyyy.", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Création de l'objet User
                User user = new User(nom, prenom, dateNaissance, sexe);

                MongoDatabase database = MongoClientConnection.connectToMongoClient();

                // Obtention de la collection
                MongoCollection<Document> collection = database.getCollection("nom_de_la_collection");

                // Création du document à insérer
                Document document = new Document();
                document.put("name", user.getName());
                document.put("surname", user.getSurname());
                document.put("birthdate", user.getBirthdate());
                document.put("sex", user.getSex());

                // Insertion du document dans la collection
                collection.insertOne(document);

                frame.dispose(); // Ferme la fenêtre après enregistrement
            }
        });

        frame.add(nomLabel);
        frame.add(nomField);
        frame.add(prenomLabel);
        frame.add(prenomField);
        frame.add(dateNaissanceLabel);
        frame.add(dateNaissanceField);
        frame.add(sexeLabel);
        frame.add(sexeComboBox);
        frame.add(new JLabel()); // Pour laisser un espace vide
        frame.add(enregistrerButton);

        frame.setVisible(true);
    }
}