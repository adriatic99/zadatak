package models;

public enum EnumTipojSifra {
	REGIJA,
	ZUPANIJA;
	public int getValue() {
		return this.ordinal() + 1;
	}
}
