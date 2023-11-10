package baker.james.word.counter.model;


import java.util.Objects;

public class CurrentWordCount {
    private String word;
    private int count;

    public CurrentWordCount(String word, int count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrentWordCount that = (CurrentWordCount) o;
        return count == that.count && Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, count);
    }

    @Override
    public String toString() {
        return "CurrentWordCount{" +
                "word='" + word + '\'' +
                ", count=" + count +
                '}';
    }
}
