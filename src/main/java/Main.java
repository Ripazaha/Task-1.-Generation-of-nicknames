import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static final int
            stringLength3 = 3,
            stringLength4 = 4,
            stringLength5 = 5;
    public static final AtomicInteger counterForLength3 = new AtomicInteger();
    public static final AtomicInteger counterForLength4 = new AtomicInteger();
    public static final AtomicInteger counterForLength5 = new AtomicInteger();

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        String replaced = text.toLowerCase().replaceAll("[^a-zA-Z0\\d-9]", "");
        String reversed = new StringBuffer(replaced).reverse().toString();
        return reversed.equals(replaced);
    }

    public static boolean consistsOfTheSameLetter(String text) {
        boolean result = true;
        for (int i = 0; i < text.length(); i++) {
            for (int j = i + 1; j < text.length(); j++) {
                if (text.charAt(i) != text.charAt(j)) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    public static boolean lettersInAscendingOrder(String text) {
        boolean result = true;
        for (int i = 0; i < text.length(); i++) {
            for (int j = i + 1; j < text.length(); j++) {
                if (text.charAt(i) > text.charAt(j)) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    private static void getAndIncrement(String text) {
        switch (text.length()) {
            case stringLength3 -> counterForLength3.getAndIncrement();
            case stringLength4 -> counterForLength4.getAndIncrement();
            case stringLength5 -> counterForLength5.getAndIncrement();
            default -> throw new IllegalStateException("Значения - " + text.length() + " не существует");
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    getAndIncrement(text);
                }
            }
        }).start();

        new Thread(() -> {
            for (String text : texts) {
                if (consistsOfTheSameLetter(text)) {
                    getAndIncrement(text);
                }
            }
        }).start();

        new Thread(() -> {
            for (String text : texts) {
                if (lettersInAscendingOrder(text)) {
                    getAndIncrement(text);
                }
            }
        }).start();

        System.out.printf("""
                Красивых слов с длиной 3: %s шт
                Красивых слов с длиной 4: %s шт
                Красивых слов с длиной 5: %s шт
                """, counterForLength3, counterForLength4, counterForLength5);
    }
}
