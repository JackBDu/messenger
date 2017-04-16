package main.java.chat;

public class User implements Comparable<User>{
	String name;
	int id;
	
	User(String uname){
		this.name = uname;
		this.id = uname.hashCode();
	}

	@Override
	public String toString() {
		return name;
	}

	public String getId() {
		return name;
	}

	public void setId(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(User u) {
		// TODO Auto-generated method stub
		return Integer.compare(this.id, u.id);
	}

	
	
	
}
