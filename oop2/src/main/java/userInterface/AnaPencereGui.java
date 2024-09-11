package userInterface;

import database.DerbyJdbcBaglantisiDb;
import domain.TelefonDomain;
import test.InitCagir;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnaPencereGui extends JFrame implements InitCagir {

    private DerbyJdbcBaglantisiDb derbyJdbcBaglantisiDb = new DerbyJdbcBaglantisiDb();
    private JList<TelefonDomain> kisilerList;

    public AnaPencereGui() {
        initPencere();
    }

    @Override
    public void initPencere() {
        JPanel jPanel = initPanel();
        JMenuBar jMenuBar = initBar();

        add(jPanel);
        setJMenuBar(jMenuBar);
        setTitle("Rehber");
        setSize(350, 500);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listeyiYenile();
    }

    @Override
    public JPanel initPanel() {
        JPanel jPanel = new JPanel(new BorderLayout());
        JPanel bulPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Kenar boşlukları

        JLabel idJlabel = new JLabel("ID:", JLabel.RIGHT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        bulPanel.add(idJlabel, gbc);
        JTextField idField = new JTextField(15);
        gbc.gridx = 1;
        bulPanel.add(idField, gbc);

        JLabel adiJlabel = new JLabel("Adı:", JLabel.RIGHT);
        gbc.gridx = 0;
        gbc.gridy = 1;
        bulPanel.add(adiJlabel, gbc);
        JTextField adiField = new JTextField(15);
        gbc.gridx = 1;
        bulPanel.add(adiField, gbc);

        JLabel soyadiJlabel = new JLabel("Soyadı:", JLabel.RIGHT);
        gbc.gridx = 0;
        gbc.gridy = 2;
        bulPanel.add(soyadiJlabel, gbc);
        JTextField soyadiField = new JTextField(15);
        gbc.gridx = 1;
        bulPanel.add(soyadiField, gbc);

        JLabel telefonJlabel = new JLabel("Telefonu:", JLabel.RIGHT);
        gbc.gridx = 0;
        gbc.gridy = 3;
        bulPanel.add(telefonJlabel, gbc);
        JTextField telefonField = new JTextField(15);
        gbc.gridx = 1;
        bulPanel.add(telefonField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout()); // FlowLayout kullanımı

        JButton bulButton = new JButton("Bul");
        buttonPanel.add(bulButton);

        JButton silButton = new JButton("Sil");
        buttonPanel.add(silButton);

        JButton düzenleButton = new JButton("Düzenle");
        buttonPanel.add(düzenleButton);

        kisilerList = new JList<>();
        kisilerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane pane = new JScrollPane(kisilerList);
        pane.setPreferredSize(new Dimension(300, 200)); // JList boyutu

        // Butonlara eylem dinleyicileri ekleme
        bulButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // ID'yi al ve parse et
                    int id = Integer.parseInt(idField.getText().trim());

                    // ID ile ilgili kişiyi bul
                    TelefonDomain bulunacakKisi = derbyJdbcBaglantisiDb.bul(id);

                    // Kayıt bulunup bulunmadığını kontrol et
                    if (bulunacakKisi == null || bulunacakKisi.getId() == 0) {
                        JOptionPane.showMessageDialog(null, "Bu ID ile ilgili kayıt bulunamadı.", "Hata", JOptionPane.ERROR_MESSAGE);
                        adiField.setText("");
                        soyadiField.setText("");
                        telefonField.setText("");
                    } else {
                        // Bilgileri text alanlarına yaz
                        adiField.setText(bulunacakKisi.getName());
                        soyadiField.setText(bulunacakKisi.getSurname());
                        telefonField.setText(bulunacakKisi.getPhoneNumber());
                        idField.setBackground(Color.cyan);
                        idField.setEditable(false);
                        idField.setToolTipText("ID 'yi Düzenleyemezsiniz.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Geçersiz ID formatı!", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        silButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelefonDomain silinecekKisi = kisilerList.getSelectedValue();
                if (silinecekKisi != null) {
                    derbyJdbcBaglantisiDb.sil(silinecekKisi);
                    listeyiYenile(); // Listeyi güncelle
                    JOptionPane.showMessageDialog(null, silinecekKisi.getName() + " kişisi başarılıyla silindi.", "Başarı", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Silinecek kişi seçilmedi!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        düzenleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idField.getText().trim());
                    String adi = adiField.getText().trim();
                    String soyadi = soyadiField.getText().trim();
                    String telefon = telefonField.getText().trim();

                    // Boş değer kontrolü
                    if (adi.isEmpty() || soyadi.isEmpty() || telefon.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Adı, soyadı ve telefon numarası boş olamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // ID'nin geçerli olup olmadığını kontrol et
                    TelefonDomain mevcutKisi = derbyJdbcBaglantisiDb.bul(id);
                    if (mevcutKisi == null) {
                        JOptionPane.showMessageDialog(null, "Geçerli bir kişi bulunamadı. Lütfen geçerli bir ID giriniz.", "Hata", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Kişiyi güncelle
                    TelefonDomain duzenlenecekKisi = new TelefonDomain();
                    duzenlenecekKisi.setId(id);
                    duzenlenecekKisi.setName(adi);
                    duzenlenecekKisi.setSurname(soyadi);
                    duzenlenecekKisi.setPhoneNumber(telefon);
                    derbyJdbcBaglantisiDb.duzenle(duzenlenecekKisi);
                    listeyiYenile(); // Listeyi güncelle
                    JOptionPane.showMessageDialog(null, "Kişi başarıyla güncellendi.", "Başarı", JOptionPane.INFORMATION_MESSAGE);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Geçersiz ID formatı!", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });








        // Panel ekleme işlemleri
        jPanel.add(pane, BorderLayout.CENTER);
        jPanel.add(bulPanel, BorderLayout.NORTH);
        jPanel.add(buttonPanel, BorderLayout.SOUTH);

        return jPanel;
    }

    @Override
    public JMenuBar initBar() {
        JMenuBar jMenuBar = new JMenuBar();

        // "Dosya" menüsünü oluşturuyoruz
        JPanel dosyaPanel = new JPanel();
        dosyaPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Menü seçeneklerini ComboBox içine ekliyoruz
        String[] menuItems = {"Kayıt Yap"};
        JComboBox<String> dosyaComboBox = new JComboBox<>(menuItems);

        // ComboBox için eylem dinleyicisi ekliyoruz
        dosyaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) dosyaComboBox.getSelectedItem();
                if ("Kayıt Yap".equals(selectedItem)) {
                    new KayitYapGui(AnaPencereGui.this); // Ana pencere referansını gönderiyoruz
                }
            }
        });

        dosyaPanel.add(dosyaComboBox);
        jMenuBar.add(dosyaPanel);

        return jMenuBar;
    }


    public void listeyiYenile() {
        kisilerList.setListData(derbyJdbcBaglantisiDb.listele().toArray(new TelefonDomain[0]));

        for (TelefonDomain tl:kisilerList.getSelectedValuesList()) {

            System.out.println("aaaa");
        }

        System.out.println("yüklenen verilrierim sayısı : "+kisilerList.getSelectedValuesList().size());


    }



}
