package io.github.unisim;

import java.time.Duration;

/**
 * A simple timer utility that can be updated on each render call.
 */
public class Timer {
  private float remainingTime;
  private float initialTime;
  private boolean hasFinished;

  /**
   * Create a new timer set to count down from an initial number of milliseconds.

   * @param initialTime - The number of milliseconds before the timer ends
   */
  public Timer(float initialTime) {
    this.initialTime = initialTime;
    remainingTime = initialTime;
    hasFinished = initialTime <= 0;
  }

  /**
   * Removes a provided timestep from the counter and returns whether the timer has stopped.

   * @param deltaTime - the time in milliseconds to remove from the counter
   * @return - true if the timer is running and the time has been decremented, false otherwise.
   */
  public boolean tick(float deltaTime) {
    remainingTime -= deltaTime;
    if (remainingTime > 0) {
      return true;
    } else {
      hasFinished = true;
      return false;
    }
  }

  /**
   * Reset the timer to its' initial time value.
   */
  public void reset() {
    remainingTime = initialTime;
    hasFinished = false;
  }

  /**
   * Return the remaining time in a String representation.

   * @return - remaining time in the form MM:SS
   */
  public String getRemainingTime() {
    // get the number of minutes and seconds from the remaining time in milliseconds.
    Duration durationRemaining = Duration.ofMillis((long) remainingTime);
    final long remainingMinutes = Math.max(durationRemaining.toMinutes(), 0);
    final long remainingSeconds = Math.max(durationRemaining.minusMinutes(remainingMinutes).getSeconds(), 0);

    return String.format("%02d:%02d", remainingMinutes, remainingSeconds);
  }

  /**
   * Return whether the timer is still running or has reached zero.

   * @return - true if the timer is running, false if the remaining time has reached zero
   */
  public boolean isRunning() {
    return !hasFinished;
  }

  /**
   * Return the initial time set for the timer.

   * @return - the initial time in milliseconds
   */
  public float getInitialTime() {
    return initialTime;
  }
}
