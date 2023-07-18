
public class program_tutma {
    private int id;
    private String program_ismi;
    private String program_tipi;
    private int program_bolum;
    private int program_uzunlugu;
    private int program_puan;

    public program_tutma(int id, String program_ismi, String program_tipi, int program_bolum, int program_uzunlugu, int program_puan) {
        this.id = id;
        this.program_ismi = program_ismi;
        this.program_tipi = program_tipi;
        this.program_bolum = program_bolum;
        this.program_uzunlugu = program_uzunlugu;
        this.program_puan = program_puan;
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

    public int getProgram_bolum() {
        return program_bolum;
    }

    public void setProgram_bolum(int program_bolum) {
        this.program_bolum = program_bolum;
    }

    public int getProgram_uzunlugu() {
        return program_uzunlugu;
    }

    public void setProgram_uzunlugu(int program_uzunlugu) {
        this.program_uzunlugu = program_uzunlugu;
    }

    public int getProgram_puan() {
        return program_puan;
    }

    public void setProgram_puan(int program_puan) {
        this.program_puan = program_puan;
    }
    
    
}
