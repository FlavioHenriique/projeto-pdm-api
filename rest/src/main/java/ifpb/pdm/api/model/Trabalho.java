package ifpb.pdm.api.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Trabalho {

    private String titulo;
    private String estado;
    private String cidade;
    private float valor;
    private String horario;
    private String data;
    private String descricao;
    private Usuario contratante;

    public Trabalho(String titulo, String estado, String cidade, float valor,
            String horario, String data, String descricao, Usuario contratante) {
        this.titulo = titulo;
        this.estado = estado;
        this.cidade = cidade;
        this.valor = valor;
        this.horario = horario;
        this.data = data;
        this.descricao = descricao;
        this.contratante = contratante;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public Usuario getContratante() {
        return contratante;
    }

    public void setContratante(Usuario contratante) {
        this.contratante = contratante;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.titulo);
        hash = 73 * hash + Objects.hashCode(this.estado);
        hash = 73 * hash + Objects.hashCode(this.cidade);
        hash = 73 * hash + Float.floatToIntBits(this.valor);
        hash = 73 * hash + Objects.hashCode(this.horario);
        hash = 73 * hash + Objects.hashCode(this.data);
        hash = 73 * hash + Objects.hashCode(this.descricao);
        hash = 73 * hash + Objects.hashCode(this.contratante);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Trabalho other = (Trabalho) obj;
        if (Float.floatToIntBits(this.valor) != Float.floatToIntBits(other.valor)) {
            return false;
        }
        if (!Objects.equals(this.titulo, other.titulo)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.cidade, other.cidade)) {
            return false;
        }
        if (!Objects.equals(this.horario, other.horario)) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.contratante, other.contratante)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Trabalho{" + "titulo=" + titulo + ", estado=" + estado + ","
                + " cidade=" + cidade + ", valor=" + valor + ", horario=" + horario +
                ", data=" + data + ", descricao=" + descricao + ", contratante=" + contratante + '}';
    }

}
