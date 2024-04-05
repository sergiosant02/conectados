import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SelectRoomPage } from './select-room.page';

describe('SelectRoomPage', () => {
  let component: SelectRoomPage;
  let fixture: ComponentFixture<SelectRoomPage>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(SelectRoomPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
