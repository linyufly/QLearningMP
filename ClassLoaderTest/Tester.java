public class Tester {
  public static void main(String[] args) {
    //ClassLoader classLoader = Animal.class.getClassLoader();

    System.out.println("So far so good.");

    try {
      //Class aClass = classLoader.loadClass("Horse");
      //System.out.println("aClass.getName() = " + aClass.getName());

      //Animal animal = aClass.newInstance();
      
      Class c = Class.forName("Horse");
      Animal animal = (Animal)c.newInstance();
      System.out.println("animal.getName() = " + animal.getName());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
