package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * La classe ClientSaisieWindow permet de créer une interface afin de
 * permettre à l'utilisateur de saisir les données à envoyer au serveur
 *
 * @author  David Delemotte
 * @version 1.0
 * @since   2017-09-12
 */

public class ClientSaisieWindow extends JFrame {

    private Datas datas;
    private JFileChooser fileChooser;
    private JTextField textField;
    private JLabel label;
    private JLabel labelProtocole;
    private JComboBox comboBoxProtocole;
    private JButton buttonFileChooser;
    private JButton buttonSend;
    String[] protocoles = { "SOURCEColl", "BYTEColl", "OBJECTColl" };

    public ClientSaisieWindow() {
        super();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        CreateElements();
        pack();
        setVisible(true);
        datas = new Datas();
    }

    private void CreateElements() {

        getContentPane().setLayout (new GridBagLayout ());
        GridBagConstraints constraints = new GridBagConstraints();

        Insets i = new Insets(10, 10, 10, 10);

        /* Création et positionnement des élements de l'interface */

        fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier java", "java");
        fileChooser.setFileFilter(filter);

        buttonFileChooser = new JButton("Sélectionner un fichier");
        constraints = setGridBagConstraints(GridBagConstraints.HORIZONTAL, i, 0, 0, 50, 10);
        getContentPane().add(buttonFileChooser, constraints);

        label = new JLabel("Classe, méthode et arguments:");
        constraints = setGridBagConstraints(GridBagConstraints.HORIZONTAL, i, 0, 20, 2, 5);
        getContentPane().add(label, constraints);

        textField = new JTextField(30);
        constraints = setGridBagConstraints(GridBagConstraints.HORIZONTAL, i, 20, 20, 2, 5);
        getContentPane().add(textField, constraints);

        labelProtocole = new JLabel("Protocole:");
        constraints = setGridBagConstraints(GridBagConstraints.HORIZONTAL, i, 0, 40, 2, 5);
        getContentPane().add(labelProtocole, constraints);

        comboBoxProtocole = new JComboBox(protocoles);
        comboBoxProtocole.setSelectedIndex(0);
        constraints = setGridBagConstraints(GridBagConstraints.HORIZONTAL, i, 20, 40, 2, 5);
        getContentPane().add(comboBoxProtocole, constraints);

        buttonSend = new JButton("Valider et envoyer");
        constraints = setGridBagConstraints(GridBagConstraints.HORIZONTAL, i, 20, 60, 2, 10);
        getContentPane().add(buttonSend, constraints);

        /* Choix du fichier */

        buttonFileChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                /* Affichage de la fenêtre de choix de fichier */
                int returnVal = fileChooser.showOpenDialog((Component)event.getSource());
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        /* Enregistrement du chemin du fichier choisi */
                        String path = fileChooser.getSelectedFile().getAbsolutePath();
                        datas.setFile(Files.readAllBytes(new File(path).toPath()));
                        /* Affichage du nom du fichier choisi */
                        buttonFileChooser.setText("Fichier sélectionné: " + fileChooser.getSelectedFile().getName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        /* Envoi des données */

        buttonSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                /* Enregistrement des informations saisies */
                datas.setInformationsExecution(textField.getText());
                datas.setProtocole(comboBoxProtocole.getSelectedItem().toString());
                if(datas.getProtocole().equals("OBJECTColl")) datas.setObject(new Calc());

                /* Lancement de la socket client */
                SocketClient socketClient = new SocketClient(datas);
            }
        });


    }

    private GridBagConstraints setGridBagConstraints(int fill, Insets insets, int gridx, int gridy, int gridwidth, int gridheight) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = fill;
        constraints.insets = insets;
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        return constraints;
    }
}
