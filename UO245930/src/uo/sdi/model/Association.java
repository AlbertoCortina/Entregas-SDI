package uo.sdi.model;

public class Association {

    public static class Perform {
	
	public static void link (User user, Task task) {
	    task._setUser(user);
	    user._getTasks().add(task);
	}
	
	public static void unlink (User user, Task task) {
	    user._getTasks().remove(task);
	    task._setUser(null);
	}
    }
    
    public static class Classify {
	
	public static void link (Category category, Task task) {
	    task._setCategory(category);
	    category._getTasks().add(task);
	}
	
	public static void unlink (Category category, Task task) {
	    category._getTasks().remove(task);
	    task._setCategory(null);
	}	
    }
    
    public static class Organize {
	
	public static void link (User user, Category category) {
	    category._setUser(user);
	    user._getCategories().add(category);
	}
	
	public static void unlink (User user, Category category) {
	    user._getCategories().remove(category);
	    category._setUser(null);
	}
    }
}