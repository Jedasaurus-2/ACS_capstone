from picozero import Stepper
from time import sleep
from machine import UART, Pin
from micropython import const

h_angle = 6
v_angle = 0

h_motor = Stepper((6,7,8,9))
v_motor = Stepper((10,11,12,13))

scanner = UART(0, baudrate=115200, tx=Pin(0), rx=Pin(1))

# The data
results = [(1,2,3,4)] * 900
i = -1

# Runs the lidar. 100% copied code, but whatever: it works. 
def get_distance():
    temp = bytes()
    if scanner.any() > 0:
        temp += scanner.read(9)
        if temp[0] == 0x59 and temp[1] == 0x59:
            distance = temp[2] + temp[3] * 256
            strength = temp[4] + temp[5] * 256
            temperature = (temp[6] + temp[7] * 256) / 8 - 256
            #return ("Distance =%5dcm, Signal Strength =%5d, Chip Temperature =%5d℃" % (distance, strength, temperature))
            return distance, strength, h_angle, v_angle

# 68 milliseconds per 6 degrees of turning
def go_to(motor, angle: float, direction: str) -> None:
    motor.turn_to(angle, direction)

# 68 milliseconds per 6 degrees of turning
def go(motor, angle: float, direction: str) -> None:
    motor.turn(angle, direction)

def main():
    global v_angle, i
    
    # Start at 6
    h_angle = 6
    
    # Turn the horizontal motor
    while h_angle < 180:
        go_to(h_motor, h_angle, "cw")
        h_angle += 6
        # "Warm Up" the sensor
        for _x in range(10):
            get_distance()
            # Actually read the sensor and save it
        results[i] = get_distance()
        # Actually send data
        print(results[i])
        i += 1
    
    # Turn the horizontal motor back
    h_angle = 0
    go(h_motor, 180, "ccw")
    
    # Turn the vertical motor once
    v_angle += 6
    go_to(v_motor, v_angle, "cw")

for _x in range(10):
    get_distance()

while v_angle < 180:
    main()

v_angle = 0
# Move back to resting location after moving...
# h_motor does this itself in main
go(v_motor, 180, "ccw")

#print("done")
#print(results)