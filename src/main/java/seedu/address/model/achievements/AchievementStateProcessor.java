package seedu.address.model.achievements;

import static seedu.address.model.achievements.AchievementState.ACHIEVED;
import static seedu.address.model.achievements.AchievementState.PREVIOUSLY_ACHIEVED;
import static seedu.address.model.achievements.DurationUnit.MONTH;
import static seedu.address.model.achievements.DurationUnit.WEEK;
import static seedu.address.model.achievements.DurationUnit.YEAR;
import static seedu.address.model.statistics.AverageType.DAILY;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import seedu.address.model.Model;
import seedu.address.model.record.RecordType;

/**
 * Class that processes the changes made to the list of achievements stored in this program, if any.
 */
public class AchievementStateProcessor {

    private static final int DAY_IN_DAYS = 1;
    private static final int WEEK_IN_DAYS = 7;
    private static final int MONTH_IN_DAYS = 30;
    private static final int YEAR_IN_DAYS = 365;

    private Model model;
    private Map<RecordType, Map<LocalDate, Double>> averageRecordMap;
    private Set<AchievementState> newStatesSet;
    private Set<RecordType> recordTypeSet;

    public AchievementStateProcessor(Model model) {
        this.model = model;
        averageRecordMap = new HashMap<>();
        newStatesSet = new HashSet<>();
        recordTypeSet = new HashSet<>(model.getAchievementsMap().keySet());
    }

    /**
     * Returns the set of changes made to the list of achievements stored in this program, if any.
     */
    public Set<AchievementState> getNewAchievementStates() {
        initialiseAverageRecordMap();

        for (RecordType recordType : model.getAchievementsMap().keySet()) {
            List<Achievement> achievementList = model.getAchievementsMap().get(recordType);
            processAchievements(achievementList, recordType);
        }
        return newStatesSet;
    }

    /**
     * Initialises a map that contains average daily records listed in descending order of date.
     */
    private void initialiseAverageRecordMap() {
        for (RecordType recordType: recordTypeSet) {
            model.calculateAverageMap(DAILY, recordType, model.getRecordList().size());

            // Sort by descending date
            Map<LocalDate, Double> averageMap = new TreeMap<>(Collections.reverseOrder());
            averageMap.putAll(model.getAverageMap());

            averageRecordMap.put(recordType, averageMap);
        }
    }

    /**
     * Sets the state of an achievement in this class and adds the new state to the set of new states stored by this
     * processor object.
     * @param achievement Achievement with state to be changed.
     * @param achievementStateToSet New achievement state of an achievement that is to be changed to.
     */
    private void setAchievementState(Achievement achievement, AchievementState achievementStateToSet) {
        if (achievement.getAchievementState() != achievementStateToSet) {
            newStatesSet.add(achievementStateToSet);
            achievement.setAchievementState(ACHIEVED);
        }
    }

    /**
     * Demotes an achievement to previously achieved, if it's current state is achieved.
     * @param achievement Achievement that is to have it's state demoted.
     */
    private void demote(Achievement achievement) {
        if (achievement.getAchievementState() == ACHIEVED) {
            setAchievementState(achievement, PREVIOUSLY_ACHIEVED);
        }
    }

    /**
     * Promotes an achievement to achieved, if it's current state is not already achieved.
     * @param achievement Achievement that is to have it's state promoted.
     */
    private void promote(Achievement achievement) {
        if (achievement.getAchievementState() != ACHIEVED) {
            setAchievementState(achievement, ACHIEVED);
        }
    }

    /**
     * Returns whether or not the requirement for an achievement has already been met.
     * @param achievement Achievement which is to be assessed on whether or not its requirement to achieve it has
     *                    been met.
     * @param daysToIterate Duration in number of days to check on whether or not achievement has met the requirement.
     * @param averageAchievementValueIterator Iterator that returns average daily values for a particular record type.
     * @return Whether or not the requirement for an achievement has already been met.
     */
    private boolean requirementIsMet(Achievement achievement, int daysToIterate,
                                            Iterator<Double> averageAchievementValueIterator) {
        boolean fulfillsRequirements = true;
        for (int i = 0; i < daysToIterate; i++) {
            Double averageAchievementValue = averageAchievementValueIterator.next();
            if (averageAchievementValue > achievement.getMaximum()
                    || averageAchievementValue < achievement.getMinimum()) {
                fulfillsRequirements = false;
                break;
            }
        }
        return fulfillsRequirements;
    }

    /**
     * Returns duration in number of days required to potentially meet the given achievement.
     * @param achievement Achievement for which duration required to assess on whether or not it has been achieved is
     *                   to be determined.
     * @return  Duration in number of days required to potentially meet the given achievement.
     */
    private int getDaysToIterate(Achievement achievement) {
        return (int) achievement.getDurationValue() * (achievement.getDurationUnits() == WEEK
                ? WEEK_IN_DAYS
                : achievement.getDurationUnits() == MONTH
                        ? MONTH_IN_DAYS
                        : achievement.getDurationUnits() == YEAR
                                ? YEAR_IN_DAYS
                                : DAY_IN_DAYS);
    }

    /**
     * Processes a list of achievements for a given record type.
     * @param achievementList List of achievements to be processed.
     * @param recordType Record type of achievements to be processed.
     */
    private void processAchievements(List<Achievement> achievementList, RecordType recordType) {
        boolean achievementIsAttained = false;
        for (Achievement achievement : achievementList) {
            assert achievement.getRecordType() == recordType : "Record type in achievements list differ from each "
                    + "other.";

            if (!achievementIsAttained) {
                achievementIsAttained = achievement.isAchieved();
            } else {
                setAchievementState(achievement, ACHIEVED);
                continue;
            }

            int daysToIterate = getDaysToIterate(achievement);
            Collection<Double> averageRecordMapAverageValues = averageRecordMap.get(recordType).values();

            if (daysToIterate > averageRecordMapAverageValues.size()) {
                demote(achievement);
            } else {
                Iterator<Double> averageAchievementValueIterator = averageRecordMapAverageValues.iterator();
                boolean fulfillsRequirements = requirementIsMet(achievement, daysToIterate,
                        averageAchievementValueIterator);
                if (fulfillsRequirements) {
                    promote(achievement);
                } else {
                    demote(achievement);
                }
            }
        }
    }

}
