package Leonardo;

public class bignumber67 {

    private String value;

    public bignumber67(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                throw new IllegalArgumentException("Input must contain only digits");
            }
        }
        // strip leading zeros (but keep at least one digit)
        int start = 0;
        while (start < input.length() - 1 && input.charAt(start) == '0') {
            start++;
        }
        this.value = input.substring(start);
    }

    public bignumber67 add(bignumber67 other) {
        String a = this.value;
        String b = other.value;

        StringBuilder result = new StringBuilder();
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;

        while (i >= 0 || j >= 0 || carry > 0) {
            int digitA = (i >= 0) ? (a.charAt(i) - '0') : 0;
            int digitB = (j >= 0) ? (b.charAt(j) - '0') : 0;

            int sum = digitA + digitB + carry;
            result.append(sum % 10);
            carry = sum / 10;

            i--;
            j--;
        }

        return new bignumber67(result.reverse().toString());
    }

    public String getValue() {
        return value;
    }
}