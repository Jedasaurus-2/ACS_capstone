from picozero import Stepper
from time import sleep
from machine import UART, Pin
from micropython import const

h_angle = 1
v_angle = 0

h_motor = Stepper((6,7,8,9))
v_motor = Stepper((10,11,12,13))

scanner = UART(0, baudrate=115200, tx=Pin(0), rx=Pin(1))

# Runs the lidar. 100% copied code, but whatever: it works. 
def get_distance():
    # Only read if we have at least 9 bytes waiting
    if scanner.any() >= 9:
        # Check for the double 0x59 header
        if scanner.read(1)[0] == 0x59:
            if scanner.read(1)[0] == 0x59:
                # Read remaining 7 bytes
                data = scanner.read(7)
                distance = data[0] + data[1] * 256
                return distance
    return None

# 68 milliseconds per 6 degrees of turning
def go_to(motor, angle: float, direction: str) -> None:
    motor.turn_to(angle, direction)

# 68 milliseconds per 6 degrees of turning
def go(motor, angle: float, direction: str) -> None:
    motor.turn(angle, direction)

def do_horizontal_sweep():
    # Start at 6
    h_angle = 1
    
    # Turn the horizontal motor
    while h_angle < 180:
        go(h_motor, 1, "cw")
        h_angle += 1
        # Empty the stale data in the buffer (while the motor
        # was turning data)
        scanner.read(scanner.any())
         
        # Try to get a reading, but give up after 100 tries
        d = None
        for _x in range(100):
            d = get_distance()
            if d is not None:
                break
            sleep(0.001) # Small delay            
        
        if d is None:
            print("(" + str(h_angle) + "," + str(v_angle) + ",0)")
        else:
            print("(" + str(h_angle) + "," + str(v_angle) + ","+str(d) + ")")
        
        # sending horizontal angle, vertical angle, distance
        # print(str(h_angle) + ","+str(v_angle)+"," + str(d))     
    
def do_negative_horizontal_sweep():
    # Start at 6
    h_angle = 180
    
    # Turn the horizontal motor
    while h_angle > 1:
        go(h_motor, 1, "ccw")
        h_angle -= 1
        # Empty the stale data in the buffer (while the motor
        # was turning data)
        scanner.read(scanner.any())
         
        # Try to get a reading, but give up after 100 tries
        d = None
        for _x in range(100):
            d = get_distance()
            if d is not None:
                break
            sleep(0.001) # Small delay            
        
        if d is None:
            print("(" + str(h_angle) + "," + str(v_angle) + ",0)")
        else:
            print("(" + str(h_angle) + "," + str(v_angle) + ","+str(d) + ")")
        
        # sending horizontal angle, vertical angle, distance
        # print(str(h_angle) + ","+str(v_angle)+"," + str(d))         

def main():
    global v_angle
    # Turn the horizontal motor back
    v_angle = 0
    while v_angle < 180:
        go_to(v_motor, v_angle, "cw")
        do_horizontal_sweep()
        v_angle += 1
        go_to(v_motor,v_angle, "cw")
        do_negative_horizontal_sweep()
        v_angle += 1
    
v_motor.turn(6, "ccw")
v_motor.turn(6, "cw")

x = 10
while x > 1:
    print(str(x))
    x -= 1
    sleep(1)
print(0)
main()
go_to(v_motor, 0, "ccw")
print("END_SIGNAL")