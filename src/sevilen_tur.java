
public class sevilen_tur {
    private int id;
    private String program_ismi;
    private String program_tipi;
    private int bolum;
    private int puan;

    public sevilen_tur(int id, String program_ismi, String program_tipi, int bolum, int puan) {
        this.id = id;
        this.program_ismi = program_ismi;
        this.program_tipi = program_tipi;
        this.bolum = bolum;
        this.puan = puan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProgram_ismi() {
        return program_ismi;
    }

    public void setProgram_ismi(String program_ismi) {
        this.program_ismi = program_ismi;
    }

    public String getProgram_tipi() {
        return program_tipi;
    }

    public void setProgram_tipi(String program_tipi) {
        this.program_tipi = program_tipi;
    }

    public int getBolum() {
        return bolum;
    }

    public void setBolum(int bolum) {
        this.bolum = bolum;
    }

    public int getPuan() {
        return puan;
    }

    public void setPuan(int puan) {
        this.puan = puan;
    }
    
    
    
}
