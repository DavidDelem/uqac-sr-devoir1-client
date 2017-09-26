package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * La classe ClientResultWindow permet de créer une interface
 * afin d'afficher le résultat renvoyé par le serveur
 *
 * @author  David Delemotte
 * @version 1.0
 * @since   2017-09-12
 */

public class ClientResultWindow extends JFrame {

    private JLabel label;

    public ClientResultWindow(String result) {
        super();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        CreateElements(result);
        pack();
        setVisible(true);
    }

    private void CreateElements(String result) {
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        Insets i = new Insets(20, 20, 20, 20);

        label = new JLabel("Réponse renvoyée par le serveur: " + result);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = i;
        constraints.gridx = 0; // Position x.
        constraints.gridy = 0; // Position y.
        constraints.gridwidth = 1; // Largeur.
        constraints.gridheight = 1; // Hauteur.
        getContentPane().add(label, constraints);

    }

}
