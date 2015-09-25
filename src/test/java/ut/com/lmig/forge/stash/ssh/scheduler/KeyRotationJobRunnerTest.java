/*
 * Copyright 2015, Liberty Mutual Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ut.com.lmig.forge.stash.ssh.scheduler;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import com.atlassian.stash.user.EscalatedSecurityContext;
import com.atlassian.stash.user.Permission;
import com.atlassian.stash.user.SecurityService;
import com.lmig.forge.stash.ssh.scheduler.KeyRotationJobRunner;
import com.lmig.forge.stash.ssh.scheduler.KeyRotationOperation;




public class KeyRotationJobRunnerTest {
   
    
    @Before
    public void setup(){
    }
    
    @Test
    public void securityElevatedOperationsIsCalledByJob(){
        SecurityService service = mock(SecurityService.class);
        KeyRotationOperation kro = mock(KeyRotationOperation.class);
        EscalatedSecurityContext esc = mock(EscalatedSecurityContext.class);
        when(service.withPermission(any(Permission.class), anyString())).thenReturn(esc);
        
        KeyRotationJobRunner jobRunner = new KeyRotationJobRunner(kro, service);
        jobRunner.runJob(null);
        
        try {
            verify(esc).call(kro);
        } catch (Throwable e) {
            fail();
        }
        
    }
    
    
    
}
