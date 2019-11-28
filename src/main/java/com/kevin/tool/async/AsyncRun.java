package com.kevin.tool.async;

import java.util.concurrent.Future;

public interface AsyncRun {

    Future run(Asyncable asyncable);
}
