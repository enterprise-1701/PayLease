package com.paylease.app.qa;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.paymentflow.ProgressBar;
import org.testng.Assert;

public class ProgressBarTester extends ScriptBase {

  private ProgressBar progressBar = new ProgressBar();

  private void assertStepNumber(int index) {
    Assert.assertTrue(progressBar.getStepNumber(index).equals(String.valueOf(index)),
        "Step number should match");
  }

  private void assertStepName(int index, String label) {
    Assert.assertTrue(progressBar.getStepName(index).equals(label), "Step name should match");
  }

  private void assertStepStatus(int index, String status) {
    Assert.assertTrue(progressBar.getStepStatus(index).equals(status), "Step status should match");
  }

  /**
   * Assert Step Name, Number and Status.
   */
  public void assertStep(int index, String label, String status) {
    assertStepNumber(index);
    assertStepName(index, label);
    assertStepStatus(index, status);
  }

  /**
   * Assert Previous steps are enabled.
   */
  public void assertPreviousStepsEnabled(int currentIndex) {
    for (int i = 1; i < currentIndex; i++) {
      assertStepStatus(i, ProgressBar.ENABLED);
    }
  }

  /**
   * Assert Future Steps are disabled.
   *
   * @param currentIndex the current step
   */
  public void assertFutureStepsDisabled(int currentIndex) {
    int stepCount = progressBar.getStepCount();
    for (int i = currentIndex + 1; i < stepCount; i++) {
      assertStepStatus(i, ProgressBar.DISABLED);
    }
  }

  /**
   * Assert any previous steps are disabled when user is on Receipt step.
   */
  public void assertPreviousStepsDisabled() {
    int i = 1;
    while (!progressBar.getStepName(i).equals("Receipt")) {
      assertStepStatus(i, ProgressBar.DISABLED);
      i++;
    }
  }
}
