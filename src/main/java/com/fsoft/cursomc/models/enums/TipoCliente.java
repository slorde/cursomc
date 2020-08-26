package com.fsoft.cursomc.models.enums;

public enum TipoCliente {
	FISICA(1, "Pessoa física"), JURIDICA(2, "Pessoa Jurídica");

	private Integer codigo;
	private String descricao;

	private TipoCliente(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static TipoCliente toEnum(Integer codigo) {
		for(TipoCliente tipoCliente: TipoCliente.values()) {
			if (tipoCliente.getCodigo().equals(codigo))
				return tipoCliente;
		}
		
		return null;
	}
}
