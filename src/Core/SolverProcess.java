package Core;

import Utils.MathUtil;

import java.util.List;

public class SolverProcess extends Thread implements Runnable {

    private Thread thisThread = null;

    private Solver solver = null;

    private Cube cubeToSolve = null;

    private List<Integer> lastSolveSolution = null;

    private SolverProcess() {
        solver = new Solver();
    }

    private static class LazyHolder {
        private static SolverProcess INSTANCE = new SolverProcess();
    }

    @Override
    public void run() {
        System.out.println("SolverProcess thread says: " + "run() started.");

        long elapsedNanos = System.nanoTime(); // elapsed time (in nanoseconds)

        lastSolveSolution = solver.solve(cubeToSolve);

        elapsedNanos = System.nanoTime() - elapsedNanos;

        System.out.println("SolverProcess thread says: " + "Completed in " + MathUtil.nanosToSeconds(elapsedNanos) + " seconds.");
    }

    public void runSolver(Cube cube) {
        this.cubeToSolve = cube;

        thisThread = new Thread(this);
        thisThread.start();
    }

    public List<Integer> getLastSolveSolution() {
        System.out.println("Solution: " + lastSolveSolution);
        return this.lastSolveSolution;
    }

    public boolean isRunning() {
        return thisThread.isAlive();
    }

    public static SolverProcess getInstance() {
        return LazyHolder.INSTANCE;
    }

}
