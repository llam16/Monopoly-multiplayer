import java.io.Serializable;

public class HashMap <K,V> implements Serializable{
	private DLList<V>[] table;

	@SuppressWarnings("unchecked")
	public HashMap(int size) {
		table = new DLList[size];
	}

	public void put(K key, V value) {
		int i = key.hashCode();
		if (table[i] == null)
			table[i] = new DLList<V>();
		table[i].add(value);
	}

	public DLList<V>[] getTable() {return table;}

	public V get(K key) {
		return table[key.hashCode()].get(0);
	}

	public String toString() {
		String string = "";
		for (int i = 1; i < table.length; i++) {
			string += "(" + i + ", " + table[i].get(0) + ")\n";
		}
		return string;
	}
}
