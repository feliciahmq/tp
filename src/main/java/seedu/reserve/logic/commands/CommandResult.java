package seedu.reserve.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.reserve.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** User Guide information should be shown to the user. */
    private final boolean showUserGuide;

    /** Statistics information should be shown to the user. */
    private final boolean showStatistics;

    /** The application should exit. */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showUserGuide, boolean showStatistics, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showUserGuide = showUserGuide;
        this.showStatistics = showStatistics;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowUserGuide() {
        return showUserGuide;
    }

    public boolean isShowStatistics() {
        return showStatistics;
    }

    public boolean isExit() {
        return exit;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showUserGuide == otherCommandResult.showUserGuide
                && showStatistics == otherCommandResult.showStatistics
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showUserGuide, showStatistics, exit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showUserGuide", showUserGuide)
                .add("showStatistics", showStatistics)
                .add("exit", exit)
                .toString();
    }

}
