import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

public class DatabaseGajiKaryawanGUI {
    // ArrayList untuk menyimpan data karyawan
    private ArrayList<Karyawan> daftarKaryawan = new ArrayList<>();

    // Variabel-variabel untuk input data karyawan
    private String nama;
    private int gajiPokok;
    private int bonus;
    private double pajakPersen;

    // Komponen GUI
    private JLabel nameLabel;
    private JLabel gajiPokokLabel;
    private JLabel bonusLabel;
    private JLabel pajakLabel;
    private JLabel gajiTotalLabel;
    private JTextField namaField;
    private JTextField gajiPokokField;
    private JTextField bonusField;
    private JTextField pajakField;
    private JButton hitungButton;
    private JButton lihatDataButton;

    // Model tabel untuk menampilkan data karyawan
    private DefaultTableModel modelTabel;
    private JTable tabelDataKaryawan;

    // Metode untuk membuat dan menampilkan GUI
    public void createAndShowGUI() {
        // Membuat frame GUI utama
        JFrame frame = new JFrame("Database Gaji Karyawan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 250);

        // Membuat panel input data karyawan
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2));

        // Inisialisasi komponen GUI seperti label, tombol, dan teksfield
        nameLabel = new JLabel("Nama: ");
        gajiPokokLabel = new JLabel("Gaji Pokok: ");
        bonusLabel = new JLabel("Bonus: ");
        pajakLabel = new JLabel("Pajak (%): ");
        gajiTotalLabel = new JLabel("Gaji Total: ");
        namaField = new JTextField();
        gajiPokokField = new JTextField();
        bonusField = new JTextField();
        pajakField = new JTextField();
        hitungButton = new JButton("Hitung Gaji");
        lihatDataButton = new JButton("Lihat Data Karyawan");

        // Menambahkan komponen ke panel input
        inputPanel.add(nameLabel);
        inputPanel.add(namaField);
        inputPanel.add(gajiPokokLabel);
        inputPanel.add(gajiPokokField);
        inputPanel.add(bonusLabel);
        inputPanel.add(bonusField);
        inputPanel.add(pajakLabel);
        inputPanel.add(pajakField);
        inputPanel.add(hitungButton);
        inputPanel.add(lihatDataButton);
        inputPanel.add(gajiTotalLabel);

        // Menambahkan panel input ke frame
        frame.add(inputPanel, BorderLayout.NORTH);

        // Menambahkan ActionListener untuk tombol "Hitung Gaji" ketika user input tidak benar
        hitungButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    nama = namaField.getText();
                    gajiPokok = Integer.parseInt(gajiPokokField.getText());
                    bonus = Integer.parseInt(bonusField.getText());
                    pajakPersen = Double.parseDouble(pajakField.getText());

                    double gajiTotal = hitungGaji();
                    gajiTotalLabel.setText("Gaji Total: " + formatRupiah(gajiTotal));

                    Karyawan karyawan = new Karyawan(nama, gajiPokok, bonus, pajakPersen, gajiTotal);
                    daftarKaryawan.add(karyawan);

                    // Setelah menambahkan data baru, refresh tabel
                    refreshTabelDataKaryawan();
                    // Kosongkan field input
                    namaField.setText("");
                    gajiPokokField.setText("");
                    bonusField.setText("");
                    pajakField.setText("");
                    // Tampilkan pesan sukses
                    JOptionPane.showMessageDialog(frame, "Hitung gaji sukses. Data sudah masuk ke dalam database.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Masukkan gaji pokok, bonus, dan pajak dalam bentuk yang benar.");
                }
            }
        });

        // Menambahkan ActionListener untuk tombol "Lihat Data Karyawan"
        lihatDataButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tampilkanTabelDataKaryawan();
            }
        });
        // Menampilkan frame utama
        frame.setVisible(true);
    }
    // Metode untuk memformat angka sebagai mata uang Rupiah
    public double hitungGaji() {
        double pajak = (pajakPersen / 100) * (gajiPokok + bonus);
        return (gajiPokok + bonus) - pajak;
    }
    // Metode untuk memformat angka sebagai mata uang Rupiah
    private String formatRupiah(double amount) {
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatRupiah.format(amount);
    }
    // Metode untuk refresh tabel data karyawan
    private void refreshTabelDataKaryawan() {
        if (modelTabel != null) {
            modelTabel.fireTableDataChanged();
        }
    }
    // Metode untuk menampilkan tabel data karyawan
    private void tampilkanTabelDataKaryawan() {
        // ... (membuat frame untuk menampilkan tabel data karyawan)
        JFrame tabelFrame = new JFrame("Data Karyawan");
        tabelFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tabelFrame.setSize(800, 400);

        modelTabel = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class; // Tipe data kolom checkbox
                } else {
                    return super.getColumnClass(columnIndex);
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // Checkbox dapat diedit, kolom lainnya tidak
            }
        };

        modelTabel.addColumn("Pilih");
        modelTabel.addColumn("Nama");
        modelTabel.addColumn("Gaji Pokok");
        modelTabel.addColumn("Bonus");
        modelTabel.addColumn("Pajak (%)");
        modelTabel.addColumn("Gaji Total");

        tabelDataKaryawan = new JTable(modelTabel);
        tabelDataKaryawan.getTableHeader().setReorderingAllowed(false);
        tabelDataKaryawan.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Tambahkan data karyawan ke tabel
        for (Karyawan karyawan : daftarKaryawan) {
            Vector<Object> rowData = new Vector<>();
            rowData.add(false); // false sebagai nilai awal JCheckBox
            rowData.add(karyawan.getNama());
            rowData.add(formatRupiah(karyawan.getGajiPokok())); // Format gaji pokok
            rowData.add(formatRupiah(karyawan.getBonus())); // Format bonus
            rowData.add(karyawan.getPajakPersen());
            rowData.add(formatRupiah(karyawan.getGajiTotal()));
            modelTabel.addRow(rowData);
        }

        JScrollPane scrollPane = new JScrollPane(tabelDataKaryawan);
        tabelFrame.add(scrollPane);

        // Tambahkan tombol "Hapus Data Terpilih" di bawah tabel
        JButton hapusDataButton = new JButton("Hapus Data Terpilih");
        hapusDataButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hapusDataTerpilih();
            }
        });
        tabelFrame.add(hapusDataButton, BorderLayout.SOUTH);

        tabelFrame.setVisible(true);
    }
    // Metode untuk menghapus data karyawan yang dipilih dari tabel
    private void hapusDataTerpilih() {
        if (modelTabel != null) {
            boolean adaDataDipilih = false; // Flag untuk menunjukkan apakah ada data yang dipilih
            int rowCount = modelTabel.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                Boolean checkboxValue = (Boolean) modelTabel.getValueAt(i, 0);
                if (checkboxValue) {
                    int konfirmasi = JOptionPane.showConfirmDialog(tabelDataKaryawan, "Yakin hapus data ini?", "Konfirmasi Hapus Data", JOptionPane.YES_NO_OPTION);
                    if (konfirmasi == JOptionPane.YES_OPTION) {
                    modelTabel.removeRow(i);
                    daftarKaryawan.remove(i);
                    }
                    adaDataDipilih = true; // Set flag menjadi true jika ada data yang dipilih
                }
            }

            if (!adaDataDipilih) {
            JOptionPane.showMessageDialog(tabelDataKaryawan, "Pilih data karyawan yang akan dihapus.");
            }
        }
    }
    // Metode main untuk menjalankan aplikasi
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Membuat objek GUI dan menampilkan GUI
                DatabaseGajiKaryawanGUI gui = new DatabaseGajiKaryawanGUI();
                gui.createAndShowGUI();
            }
        });
    }
}
