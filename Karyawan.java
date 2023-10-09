public class Karyawan {
    // Variabel-variabel instance untuk menyimpan data karyawan
    private String nama;          
    private int gajiPokok;        
    private int bonus;            
    private double pajakPersen;   
    private double gajiTotal;     

    // Konstruktor untuk inisialisasi objek Karyawan
    public Karyawan(String nama, int gajiPokok, int bonus, double pajakPersen, double gajiTotal) {
        this.nama = nama;
        this.gajiPokok = gajiPokok;
        this.bonus = bonus;
        this.pajakPersen = pajakPersen;
        this.gajiTotal = gajiTotal;
    }

    // Method getter untuk mengambil nilai nama karyawan
    public String getNama() {
        return nama;
    }

    // Method getter untuk mengambil nilai gaji pokok karyawan
    public int getGajiPokok() {
        return gajiPokok;
    }

    // Method getter untuk mengambil nilai bonus karyawan
    public int getBonus() {
        return bonus;
    }

    // Method getter untuk mengambil nilai persentase pajak
    public double getPajakPersen() {
        return pajakPersen;
    }

    // Method getter untuk mengambil nilai total gaji karyawan
    public double getGajiTotal() {
        return gajiTotal;
    }
}
