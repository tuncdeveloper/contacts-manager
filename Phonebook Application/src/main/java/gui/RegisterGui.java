package gui;

import domain.Person;
import service.PersonService;
import test.Initialize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterGui extends JDialog implements Initialize {

    private final PersonService personService;

    private final Person person ;
    private final MenuGui menuGui;

    public RegisterGui(MenuGui menuGui) {
        this.personService = new PersonService();
        this.person=new Person();
        this.menuGui = menuGui;
        initPencere();
    }

    @Override
    public void initPencere() {
        JPanel jPanel = initPanel();
        add(jPanel);
        setTitle("Kayıt Yap");
        pack();
        setLocationRelativeTo(null);
        setModalityType(DEFAULT_MODALITY_TYPE);
        setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    @Override
    public JPanel initPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Boşluklar
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Adı alanı
        JLabel adiLabel = new JLabel("Adı: ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(adiLabel, gbc);
        JTextField adiField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(adiField, gbc);

        // Soyadı alanı
        JLabel soyadiLabel = new JLabel("Soyad: ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(soyadiLabel, gbc);
        JTextField soyadiField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(soyadiField, gbc);

        // Telefon alanı
        JLabel telefonLabel = new JLabel("Telefon: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(telefonLabel, gbc);
        JTextField telefonField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(telefonField, gbc);

        // Buton paneli
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton kaydetButton = new JButton("Kaydet");
        buttonPanel.add(kaydetButton);
        JButton iptalButton = new JButton("İptal");
        buttonPanel.add(iptalButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        // Kaydet butonuna tıklama eylemi
        kaydetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = adiField.getText().trim();
                String surname = soyadiField.getText().trim();
                String phoneNumber = telefonField.getText().trim();

                // Ad, soyad ve telefon alanlarının boş olup olmadığını kontrol edin
                if (name.isEmpty() || surname.isEmpty() || phoneNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ad, Soyad ve Telefon alanları boş bırakılamaz!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                } else {
                    // Tüm alanlar doldurulmuşsa kaydetme işlemi
                    person.setName(name);
                    person.setSurname(surname);
                    person.setPhoneNumber(phoneNumber);

                    personService.addPerson(person);

                    JOptionPane.showMessageDialog(null, person.getName() +
                            " " + person.getSurname() +
                            " kişisi başarılı bir şekilde kaydedildi\nTelefon numarası: " + person.getPhoneNumber());

                    menuGui.listeyiYenile();
                    dispose();
                }
            }
        });

        // İptal butonuna tıklama eylemi
        iptalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        return panel;
    }

    @Override
    public JMenuBar initBar() {
        return null;
    }
}
