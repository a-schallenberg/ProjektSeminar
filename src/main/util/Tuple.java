package main.util;

import java.lang.reflect.Constructor;

public class Tuple <T, U> {
	public T t;
	public U u;

	public Tuple(T t, U u) {
		this.t = t;
		this.u = u;
	}
}
