package javaNftGenerator;

import java.util.HashMap;

public class ComponentMap extends HashMap<String, Component> {

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ComponentMap == false) {
			return false;
		} else {
			ComponentMap map = (ComponentMap) obj;
			for (String key : this.keySet()) {
				if (this.get(key).matches(map.get(key)) == false) {
					return false;
				}
			}
			return true;
		}
	}

}
