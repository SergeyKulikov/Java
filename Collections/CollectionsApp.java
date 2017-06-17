import java.util.*;

/**
	1. Создать массив с набором слов (20-30 слов, должны встречаться повторяющиеся):

    Посчитать сколько раз встречается каждое слово;
    Найти список слов, из которых состоит текст (дубликаты не считать);

	2. Написать простой класс ТелефонныйСправочник:

    В каждой записи всего три поля: Фамилия, Телефон, email;
    Отдельный метод для поиска номера телефона по фамилии (ввели фамилию, получили телефон), и отдельный метод для поиска email по фамилии. Следует учесть, что под одной фамилией может быть несколько записей.

   @author (Sergey Kulikov)
   @version (15.06.2017)
 */

public class CollectionsApp {
	static String article = "For us Earthlings, life under a single Sun is just the way it is. " +
		"But with the development of modern astronomy, we've become aware of the fact " +
		"that the Universe is filled with binary and even triple star systems. " +
		"Hence, if life does exist on planets beyond our Solar System, much of it could be " +
		"accustomed to growing up under two or even three suns. For centuries, " +
		"astronomers have wondered why this difference exists and how star systems came to be.";
		
	static String[] words;
	static HashSet<String> unq = new HashSet<String>();
	static StringBuilder sb = new StringBuilder();
	
	public static void main(String [] arg) {
		words = article.toUpperCase().split("\\p{P}?[ ,.:!;]+");
		for (String s: words) sb.append(s+" ");	
		
		for (int i=0; i<words.length; i++) {
			unq.add(words[i]);
			long count = Arrays.stream(sb.toString().split("\\s")).filter(words[i]::equals).count();
			System.out.println(words[i]+": "+count);
		}	
		System.out.printf("We have %d elemets in the list, but only %d of them are unique.\n\n\n", words.length, unq.size());
		
		System.out.println("Varant 1 -------------- ");
		PhoneBook book = new PhoneBook();
		book.add("Ivanov", "+79260987654", "ivanov@gmail.com");
		book.add("Petrov", "+79031112233", "perov@mail.ru");
		book.add("Rabinovich", "+180098765512", "rabinovich@microsoft.com");
		book.add("Ivanov", "+79260987684", "ivanov89@gmail.com");
		
		System.out.println(book.getPhonesByName("Ivanov"));
		System.out.println(book.getEmailsByName("Ivanov"));
		System.out.println(book.getEmailsByName("Rabinovich"));
		
		System.out.println("Varant 2 -------------- ");
		PhoneBookMap book1 = new PhoneBookMap();
		book1.add("Rabinovich", "+79260987654", "rabinovich@gmail.com");
		book1.add("Petrov", "+79031112233", "perov@mail.ru");
		book1.add("Rabinovich", "+180098765512", "rabinovich@microsoft.com");
		book1.add("Ivanov", "+79260987684", "ivanov89@gmail.com");
		
		System.out.println(book1.getPhonesByName("Petrov"));
		System.out.println(book1.getPhonesByName("Rabinovich"));
		System.out.println(book1.getEmailsByName("Rabinovich"));
		
	}
	
	static class PhoneBookItem {
		public String secondName;
		public String phone;
		public String email;
	}
	
	static class PhoneBook {
		public List<PhoneBookItem> items = new ArrayList<PhoneBookItem>();
		
		public void add(String secondName, String phone, String email) {
			PhoneBookItem item = new PhoneBookItem();
			item.secondName = secondName;
			item.phone = phone;
			item.email = email;
			items.add(item);
		}

		String getPhonesByName(String secondName) {
			ArrayList<String> tmp = new ArrayList<String>();
			for (int i=0; i<items.size(); i++) {
				if (items.get(i).secondName == secondName) 
					tmp.add(items.get(i).phone);
			}
			return (secondName+"'s phones "+(tmp.size()>1 ? "are " : "is ")+tmp.toString());
		}
		
		String getEmailsByName(String secondName) {
			ArrayList<String> tmp = new ArrayList<String>();
			for (int i=0; i<items.size(); i++) {
				if (items.get(i).secondName == secondName) 
					tmp.add(items.get(i).email);
			}
			return (secondName+"'s e-mails "+(tmp.size()>1 ? "are " : "is ")+tmp.toString());
		}
	}
	
	
	static class PhoneBookMap {
		public Map<String, ArrayList<PhoneBookItem>> items = new TreeMap<String, ArrayList<PhoneBookItem>>();
		
		public void add(String secondName, String phone, String email) {
			PhoneBookItem item = new PhoneBookItem();
				
			item.secondName = secondName;
			item.phone = phone;
			item.email = email;
			
			if (!items.containsKey(secondName)) {
				ArrayList<PhoneBookItem> list = new ArrayList<PhoneBookItem>();
				list.add(item);
				items.put(secondName, list);
			}
			else {
				ArrayList<PhoneBookItem> list = items.get(secondName);
				list.add(item);
			}
		}

		String getPhonesByName(String secondName) {
			ArrayList<String> tmp = new ArrayList<String>();
			ArrayList<PhoneBookItem> list = items.get(secondName);
			for (int i=0; i<list.size(); i++) {
				tmp.add(list.get(i).phone);
			}
			return (secondName+"'s phones "+(tmp.size()>1 ? "are " : "is ")+tmp.toString());
		}
		
		String getEmailsByName(String secondName) {
			ArrayList<String> tmp = new ArrayList<String>();
			ArrayList<PhoneBookItem> list = items.get(secondName);
			for (int i=0; i<list.size(); i++) {
				tmp.add(list.get(i).email);
			}
			return (secondName+"'s e-mails "+(tmp.size()>1 ? "are " : "is ")+tmp.toString());
		}
	}
}