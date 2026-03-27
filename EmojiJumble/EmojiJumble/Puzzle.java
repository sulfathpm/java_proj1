public class Puzzle {
    public final String[] jumbled;
    public final String[] correct;
    public final String meaning;

    public Puzzle(String[] jumbled, String[] correct, String meaning) {
        this.jumbled = jumbled;
        this.correct = correct;
        this.meaning = meaning;
    }

    public static Puzzle[] ALL = {
        new Puzzle(
            new String[]{"💰", "🌳", "🚫", "🌱"},
            new String[]{"💰", "🚫","🌱","🌳"},
            "Money doesn't grow on trees"
        ),
        new Puzzle(
            new String[]{"🤐", "🥇", "🔊", "🥈"},
            new String[]{"🤐", "🥇", "🔊", "🥈"}, 
            "Silence is gold, speech is silver"
        ),
        new Puzzle(
            new String[]{"😊", "🚫", "☔", "🌧️"},
            new String[]{"🚫", "🌧️", "☔", "😊"},
            "Be happy even in the rain"
        ),
        new Puzzle(
            new String[]{"🍳", "🥚", "🔨", "🚫"},
            new String[]{"🚫", "🔨", "🥚", "🍳"},
            "You can't make an omelet without breaking eggs"
        ),
        new Puzzle(
            new String[]{"🏆", "🏃", "🎯", "💨"},
            new String[]{"🏃", "💨", "🎯", "🏆"},
            "Run fast to reach your goal"
        ),
        new Puzzle(
            new String[]{"⏰", "❤️", "👨‍👩‍👧‍👦"},
            new String[]{"❤️", "⏰", "👨‍👩‍👧‍👦"},
            "Spend time with family"
        ),
        new Puzzle(
            new String[]{"😌", "📱", "🚫", "🧠"},
            new String[]{"🚫", "📱", "🧠", "😌"},
            "Reduce phone usage for peace of mind"
        ),
        new Puzzle(
            new String[]{"🌳", "⏳", "🌱"},
            new String[]{"🌱", "⏳", "🌳"},
            "Growth takes time"
        ),
        new Puzzle(
            new String[]{"🐱", "🎒", "🔓", "🗣️"},
            new String[]{"🔓", "🎒", "🐱", "🗣️"},
            "Let the cat out of the bag"
    ),
        new Puzzle(
            new String[]{"⏰", "🐦", "🐛", "😋"},
            new String[]{"⏰", "🐦", "😋", "🐛"},
            "The early bird catches the worm"
        )
    };
}
