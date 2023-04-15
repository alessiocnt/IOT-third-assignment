#include "Scheduler.h"
#include <Ticker.h>

volatile bool timerFlag;

void timerHandler(void)
{
    timerFlag = true;
}

void Scheduler::init(int basePeriod)
{
    this->basePeriod = basePeriod;
    timerFlag = false;
    // Handler richiamato ogni 250ms
    timer1_attachInterrupt(timerHandler);
    timer1_enable(TIM_DIV16, TIM_EDGE, TIM_LOOP);
    timer1_write(5000* basePeriod);
}

void Scheduler::addTask(Task *task)
{
    taskList.add(task);
}

void Scheduler::schedule()
{
    while (!timerFlag) {}
    timerFlag = false;

    for (int i = 0; i < taskList.size(); i++)
    {
        Task *it = taskList.get(i);
        if (it->isActive() && it->updateAndCheckTime(basePeriod))
        {
            it->tick();
        }
    }
}
