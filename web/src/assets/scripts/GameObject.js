const GAME_OBJECTS = [];

export class GameObject {
    constructor() {
        GAME_OBJECTS.push(this);
        this.timedelta = 0;
        this.has_called_start = false;
    }

    start() { // 只执行一次

    }

    update() { // 除了第一帧其他都执行

    }

    on_destroy() { // 销毁前执行

    }

    destroy() {
        this.on_destroy()

        for (let i in GAME_OBJECTS) {
            if (GAME_OBJECTS[i] === this) {
                GAME_OBJECTS.splice(i);
                break;
            }
        }
    }
}

let last_timestamp;
const step = timestamp => {
    for (let obj of GAME_OBJECTS) {
        if (!obj.has_called_start) {
            obj.start();
            obj.has_called_start = true;
        } else {
            obj.timedelta = timestamp - last_timestamp;
            obj.update();
        }
    }
    last_timestamp = timestamp;

    requestAnimationFrame(step);
}
requestAnimationFrame(step);